@file:Suppress("FunctionName")

package bg.dalexiev.rss.common.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.infinitelambda.app.core.ui.theme.RssReaderTheme

@Composable
fun App() {
    RssReaderTheme {
        Scaffold(
            topBar = { RssReaderTopBar() }
        ) {
            AppContent(it)
        }
    }
}

@Composable
fun RssReaderTopBar() {
    TopAppBar(
        title = { Text(text = "Jetpack Compose Multiplatform Demo") }
    )
}

@Composable
expect fun AppContent(innerPadding: PaddingValues)