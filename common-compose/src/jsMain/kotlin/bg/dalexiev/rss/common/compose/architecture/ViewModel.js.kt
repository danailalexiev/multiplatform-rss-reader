package bg.dalexiev.rss.common.compose.architecture

actual abstract class ViewModel actual constructor() {

    private val closableTags: MutableMap<String, Any> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getTag(key: String): T? = closableTags[key] as T?

    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> putTagIfAbsent(key: String, tag: T): T =
        closableTags.getOrPut(key) { tag } as T

    internal fun clear() {
        closableTags
            .filter { it.value is Closable }
            .map { it as Closable }
            .forEach { it.close() }

        onCleared()
    }

    protected open fun onCleared() {
        // do nothing
    }

}