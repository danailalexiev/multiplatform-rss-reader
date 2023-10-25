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
import kotlinx.html.js.onClickFunction
import mdc.circularProgress.MDCCircularProgress
import org.w3c.dom.HTMLElement

private val dependencies = dependencies(true)

private val scope = MainScope()

fun main() {
    val progressIndicator = MDCCircularProgress.attachTo(document.getElementById("progressIndicator") as? HTMLElement)
    window.onload = {
        loadFeed {
            progressIndicator.close()
            renderFeed(it) {
                window.open(url = it.link.value, target = "_blank")
            }
        }
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

private fun hideProgressIndicator() {
    document.getElementById("progressIndicator")?.classList?.add("mdc-circular-progress--closed")
}

private fun renderFeed(items: List<Item>, onClick: (Item) -> Unit) {
    document.getElementById("root")?.let { root ->
        items.forEach {
            root.append(renderItem(it, onClick))
        }
    }
}

private fun renderItem(item: Item, onClick: (Item) -> Unit) = document.create.article {
    classes = setOf("mdc-card", "mdc-theme--surface", "mdc-theme--on-surface", "card")

    div {
        classes = setOf("mdc-card__primary-action")
        tabIndex = "0"

        header {
            h1 {
                classes = setOf("mdc-typography--headline5")

                text(item.title.value)
            }
            p {
                classes = setOf("mdc-typography--caption")

                text(item.author.name)
            }
        }
        p {
            classes = setOf("mdc-typography--body1")

            text(item.description.value)
        }

        div {
            classes = setOf("mdc-card__ripple")
        }
    }

    onClickFunction = { onClick(item) }
}

