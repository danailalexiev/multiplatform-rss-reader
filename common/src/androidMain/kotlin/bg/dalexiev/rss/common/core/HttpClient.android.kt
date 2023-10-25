package bg.dalexiev.rss.common.core

import android.util.Log
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.logging.*

internal actual fun createHttpEngine(): HttpClientEngine = OkHttp.create()

internal actual fun createHttpLogger(): Logger = object: Logger {
    override fun log(message: String) {
        Log.d("RSS HTTP", message)
    }

}