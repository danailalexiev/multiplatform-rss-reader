@file:Suppress("FunctionName")

package bg.dalexiev.rss.common.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import bg.dalexiev.rss.common.Dependencies
import bg.dalexiev.rss.common.compose.architecture.mutableCreationExtrasOf
import bg.dalexiev.rss.common.compose.architecture.viewModel
import bg.dalexiev.rss.common.compose.feed.Factory
import bg.dalexiev.rss.common.compose.feed.Feed
import bg.dalexiev.rss.common.compose.feed.FeedViewModel
import bg.dalexiev.rss.common.compose.feed.SOURCE_URL_KEY
import bg.dalexiev.rss.common.data.SourceUrl
import kotlinx.browser.window

val LocalDependencies = staticCompositionLocalOf<Dependencies> { error("no dependencies provided") }

@Composable
actual fun AppContent(innerPadding: PaddingValues) {
    Feed(
        viewModel = viewModel(
            factory = FeedViewModel.Factory,
            extras = mutableCreationExtrasOf(
                FeedViewModel.SOURCE_URL_KEY to SourceUrl("https://cors-anywhere.herokuapp.com/https://stackoverflow.blog/feed/")
            )
        ),
        padding = innerPadding
    ) {
        window.open(url = it.link.value, target = "_blank")
    }
}