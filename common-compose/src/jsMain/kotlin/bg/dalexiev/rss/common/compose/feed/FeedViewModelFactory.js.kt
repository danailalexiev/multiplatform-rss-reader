package bg.dalexiev.rss.common.compose.feed

import bg.dalexiev.rss.common.compose.architecture.CreationExtras
import bg.dalexiev.rss.common.compose.architecture.ViewModelFactory
import bg.dalexiev.rss.common.compose.architecture.ViewModelFactory.Companion.DEPENDENCIES_KEY
import bg.dalexiev.rss.common.data.SourceUrl
import kotlinx.coroutines.Dispatchers

private object SourceUrlKeyImpl : CreationExtras.Key<SourceUrl>

internal val FeedViewModel.Companion.SOURCE_URL_KEY: CreationExtras.Key<SourceUrl>
    get() = SourceUrlKeyImpl

internal val FeedViewModel.Companion.Factory: ViewModelFactory<FeedViewModel>
    get() = object : ViewModelFactory<FeedViewModel> {
        override fun create(extras: CreationExtras): FeedViewModel =
            FeedViewModel(
                dependencies = extras[DEPENDENCIES_KEY]
                    ?: throw IllegalStateException("Dependencies not provided"),
                sourceUrl = extras[SOURCE_URL_KEY]
                    ?: throw IllegalStateException("Source URL not provided"),
                ioDispatcher = Dispatchers.Default
            )

    }