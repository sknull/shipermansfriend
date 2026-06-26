package de.visualdigits.shipermansfriend.data.http

import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    private val log = co.touchlab.kermit.Logger.withTag("HttpClientFactory")

    fun create(
        engine: HttpClientEngine,
               settingsRepositoryProvider: () -> SettingsRepository
    ): HttpClient {
        return HttpClient(engine) {
            install(WebSockets)
            install(UserAgent) {
                agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
                socketTimeoutMillis = 10000
                connectTimeoutMillis = 10000
            }
            install(ContentEncoding) {
                deflate(1.0F)
                gzip(0.9F)
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.NONE
                logger = object : Logger {
                    override fun log(message: String) {
                        log.d(message)
                    }
                }
            }
            defaultRequest {
//                header("Accept", "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8")
                header("Accept-Encoding", "gzip, deflate")
                header("Accept-Language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7")
            }
        }
    }
}
