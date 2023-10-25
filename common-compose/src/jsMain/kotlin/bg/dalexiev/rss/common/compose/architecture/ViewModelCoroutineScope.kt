package bg.dalexiev.rss.common.compose.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private const val JOB_KEY = "bg.dalexiev.rss.common.compose.architecture.ViewModelCoroutineScope.JOB_KEY"

actual val ViewModel.viewModelScope: CoroutineScope
    get() = getTag(JOB_KEY) ?: putTagIfAbsent(JOB_KEY, CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate))

internal class CloseableCoroutineScope(context: CoroutineContext): Closable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }

}
