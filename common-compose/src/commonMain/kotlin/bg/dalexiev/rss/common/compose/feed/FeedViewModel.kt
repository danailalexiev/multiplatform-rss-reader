package bg.dalexiev.rss.common.compose.feed

import androidx.compose.runtime.Immutable
import bg.dalexiev.rss.common.Dependencies
import bg.dalexiev.rss.common.compose.architecture.ViewModel
import bg.dalexiev.rss.common.compose.architecture.viewModelScope
import bg.dalexiev.rss.common.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

@Immutable
sealed interface FeedUiState {

    data object Loading : FeedUiState

    data class Loaded(val items: List<Item>) : FeedUiState

    data object Error : FeedUiState

}

class FeedViewModel(
    private val dependencies: Dependencies,
    private val sourceUrl: SourceUrl,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val uiState by lazy {
        flow { emit(fetchState()) }
            .flowOn(ioDispatcher)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FeedUiState.Loading)
    }

    private suspend fun fetchState(): FeedUiState = dependencies.getRssFeed(
        Source(
            sourceUrl,
            SourceType.RSS
        )
    ).fold(
        {
            it
            FeedUiState.Error
        },
        { FeedUiState.Loaded(it.items) }
    )

    companion object
}