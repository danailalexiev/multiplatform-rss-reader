package bg.dalexiev.rss.common.compose.architecture

import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel()

expect val ViewModel.viewModelScope: CoroutineScope