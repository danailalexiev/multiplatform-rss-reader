package bg.dalexiev.rss.common.core

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.logging.*

internal actual fun createHttpEngine(): HttpClientEngine = Js.create()

internal actual fun createHttpLogger(): Logger = object: Logger {

    override fun log(message: String) {
        console.log("RSS HTTP", message)
    }

}