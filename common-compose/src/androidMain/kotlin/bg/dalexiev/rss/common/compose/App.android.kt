@file:Suppress("FunctionName")

package bg.dalexiev.rss.common.compose

import android.app.Application
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import bg.dalexiev.rss.common.compose.feed.Factory
import bg.dalexiev.rss.common.compose.feed.Feed
import bg.dalexiev.rss.common.compose.feed.FeedViewModel
import bg.dalexiev.rss.common.compose.feed.SOURCE_URL_KEY
import bg.dalexiev.rss.common.data.SourceUrl

@Composable
actual fun AppContent(innerPadding: PaddingValues) {
    val uriHandler = LocalUriHandler.current
    val app = LocalContext.current.applicationContext as Application

    Feed(
        viewModel = viewModel(
            factory = FeedViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(APPLICATION_KEY, app)
                set(FeedViewModel.Companion.SOURCE_URL_KEY, SourceUrl("https://stackoverflow.blog/feed/"))
            }
        ),
        padding = innerPadding,
        onClick = { uriHandler.openUri(it.link.value) }
    )
}