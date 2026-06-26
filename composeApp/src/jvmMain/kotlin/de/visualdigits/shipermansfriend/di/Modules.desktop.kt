package de.visualdigits.shipermansfriend.di

import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.domain.model.JvmCryptoBox
import de.visualdigits.common.presentation.components.ConnectivityManager
import de.visualdigits.shipermansfriend.data.database.DriverFactory
import de.visualdigits.shipermansfriend.data.http.HttpClientFactory
import de.visualdigits.shipermansfriend.data.repository.ImageCache
import de.visualdigits.shipermansfriend.domain.repository.JvmLocationProvider
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

actual val homeDirectory: String
    get() = File(System.getProperty("user.home"), ".shipermansfriend").canonicalPath

actual val platformModule: Module
    get() = module {
        single<CryptoBox> { JvmCryptoBox(get<String>(named("homeDirectory"))) }

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

        single { DriverFactory() }
        single { ConnectivityManager() }
        single {
            ImageCache(
                basePath = get<String>(named("homeDirectory")),
                context = coil3.PlatformContext.INSTANCE
            )
        }
        singleOf(::JvmLocationProvider).bind<LocationProvider>()
    }
