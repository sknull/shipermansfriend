package de.visualdigits.shipermansfriend.di

import android.annotation.SuppressLint
import de.visualdigits.common.domain.model.AndroidCryptoBox
import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.presentation.components.ConnectivityManager
import de.visualdigits.shipermansfriend.data.database.DriverFactory
import de.visualdigits.shipermansfriend.data.http.HttpClientFactory
import de.visualdigits.shipermansfriend.data.repository.ImageCache
import de.visualdigits.shipermansfriend.domain.repository.AndroidLocationProvider
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private val TRUSTED_HOSTS = listOf("aisstream.io", "aisuptime.buttermilkgreen.fyi")

actual val homeDirectory: String
    get() = ""

actual val platformModule: Module
    get() = module {
        // Einheitlicher Name passend zum sharedModule
        single(named("homeDirectory")) {
            File(System.getProperty("user.home"), ".shipermansfriend").canonicalPath
        }

        single<CryptoBox> { AndroidCryptoBox(get<String>(named("homeDirectory"))) }

        single<HttpClientEngine> {
            val trustAllCerts = @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }

            val sslContext = SSLContext.getInstance("SSL").apply {
                init(null, arrayOf<TrustManager>(trustAllCerts), SecureRandom())
            }

            OkHttp.create {
                config {
                    sslSocketFactory(sslContext.socketFactory, trustAllCerts)
                    hostnameVerifier { hostname, _ -> TRUSTED_HOSTS.contains(hostname.lowercase()) || hostname.endsWith(".aisstream.io")  }

                    followRedirects(true)
                    followSslRedirects(true)

                    dispatcher(okhttp3.Dispatcher().apply { maxRequestsPerHost = 4 })
                }
            }
        }

        single {
            HttpClientFactory.create(
                engine = get()
            )
        }

        single { DriverFactory(context = androidApplication()) }
        single { ConnectivityManager(context = get()) }
        singleOf(::ImageCache)
        singleOf(::AndroidLocationProvider).bind<LocationProvider>()

        single<GadulkaPlayer> { GadulkaPlayer() }

        single<AudioStorage> { AndroidAudioStorage(get()) }
    }
