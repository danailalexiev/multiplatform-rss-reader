package bg.dalexiev.rss.common

import bg.dalexiev.rss.common.core.createHttpClient
import bg.dalexiev.rss.common.core.createHttpEngine
import bg.dalexiev.rss.common.core.createHttpLogger
import io.ktor.client.*

class Dependencies internal constructor(
    val httpClient: HttpClient
)

fun dependencies(isHttpLoggingEnabled: Boolean = false): Dependencies {
    val httpEngine = createHttpEngine()
    val httpLogger = createHttpLogger()
    val httpClient = createHttpClient(httpEngine, httpLogger, isHttpLoggingEnabled)
    return Dependencies(httpClient)
}
