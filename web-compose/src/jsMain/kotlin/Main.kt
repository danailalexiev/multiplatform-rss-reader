import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import bg.dalexiev.rss.common.compose.App
import bg.dalexiev.rss.common.compose.LocalDependencies
import bg.dalexiev.rss.common.dependencies
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val dependencies = dependencies(true);

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "composeTarget") {
            CompositionLocalProvider(LocalDependencies provides dependencies) {
                App()
            }
        }
    }
}

