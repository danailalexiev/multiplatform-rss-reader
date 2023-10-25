package bg.dalexiev.rss.common.compose.feed

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bg.dalexiev.rss.common.compose.DependenciesProvider
import bg.dalexiev.rss.common.data.SourceUrl
import kotlinx.coroutines.Dispatchers

private object SourceUrlKeyImpl : CreationExtras.Key<SourceUrl>

internal val FeedViewModel.Companion.SOURCE_URL_KEY: CreationExtras.Key<SourceUrl>
    get() = SourceUrlKeyImpl

internal val FeedViewModel.Companion.Factory: ViewModelProvider.Factory
    get() = viewModelFactory {
        initializer {
            val dependencies = (this[APPLICATION_KEY] as DependenciesProvider).dependencies
            val sourceUrl = this[SOURCE_URL_KEY] ?: throw IllegalStateException("Source URL not provided")

            FeedViewModel(
                dependencies = dependencies,
                sourceUrl = sourceUrl,
                ioDispatcher = Dispatchers.IO
            )
        }
    }