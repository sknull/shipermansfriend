package de.visualdigits.shipermansfriend.di

import de.visualdigits.common.domain.model.AndroidCryptoBox
import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.presentation.components.ConnectivityManager
import de.visualdigits.shipermansfriend.data.database.DriverFactory
import de.visualdigits.shipermansfriend.data.http.HttpClientFactory
import de.visualdigits.shipermansfriend.data.repository.ImageCache
import de.visualdigits.shipermansfriend.domain.repository.AndroidLocationProvider
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

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
            OkHttp.create {
                config {
                    followRedirects(true)
                    followSslRedirects(true)
                    dispatcher(okhttp3.Dispatcher().apply { maxRequestsPerHost = 4 })
                }
            }
        }

        single {
            HttpClientFactory.create(
                engine = get(),
                settingsRepositoryProvider = { get() }
            )
        }

        single { DriverFactory(context = androidApplication()) }
        single { ConnectivityManager(context = get()) }
        singleOf(::ImageCache)
        singleOf(::AndroidLocationProvider).bind<LocationProvider>()
    }
