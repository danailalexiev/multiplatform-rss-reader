package bg.dalexiev.rss.common.compose.architecture

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope


actual typealias ViewModel = androidx.lifecycle.ViewModel

actual val ViewModel.viewModelScope: CoroutineScope
    get() = this.viewModelScope
