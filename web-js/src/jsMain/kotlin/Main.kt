import bg.dalexiev.rss.common.data.*
import bg.dalexiev.rss.common.dependencies
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.html.*
import kotlinx.html.dom.create

private val dependencies = dependencies(true)

private val scope = MainScope()

fun main() {
    window.onload = {
        loadFeed { renderItems(it) }
    }
}

private fun loadFeed(onSuccess: (List<Item>) -> Unit) = scope.launch {
    withContext(Dispatchers.Default) {
        dependencies.getRssFeed(
            Source(
                SourceUrl("https://cors-anywhere.herokuapp.com/https://stackoverflow.blog/feed/"),
                SourceType.RSS
            )
        )
    }.onRight { onSuccess(it.items) }
}

private fun renderItems(items: List<Item>) {
    document.getElementById("root")?.let { root ->
        items.forEach {
            root.append(
                document.create.article {
                    header {
                        h1 { text(it.title.value) }
                        p { text(it.author.name) }
                    }
                    p { text(it.description.value) }
                    a {
                        href = it.link.value
                        target = "blanc"
                        text("Read more...")
                    }
                }
            )
        }
    }
}

