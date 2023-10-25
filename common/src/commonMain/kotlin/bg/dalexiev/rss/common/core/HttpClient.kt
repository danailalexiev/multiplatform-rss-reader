package bg.dalexiev.rss.common.core

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

internal expect fun createHttpEngine(): HttpClientEngine

internal expect fun createHttpLogger(): Logger

internal fun createHttpClient(engine: HttpClientEngine, logger: Logger, isLoggingEnabled: Boolean): HttpClient =
    HttpClient(engine) {

        defaultRequest {
            header(HttpHeaders.Accept, ContentType.Application.Xml)
        }

        install(Logging) {
            this.level = if (isLoggingEnabled) {
                LogLevel.BODY
            } else {
                LogLevel.ALL
            }
            this.logger = logger
        }

    }