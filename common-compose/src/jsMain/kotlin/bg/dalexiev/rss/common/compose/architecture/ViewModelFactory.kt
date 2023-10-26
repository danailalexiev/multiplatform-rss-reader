package bg.dalexiev.rss.common.compose.architecture

import androidx.compose.runtime.Composable
import bg.dalexiev.rss.common.Dependencies
import bg.dalexiev.rss.common.compose.LocalDependencies
import bg.dalexiev.rss.common.compose.architecture.ViewModelFactory.Companion.DEPENDENCIES_KEY

internal abstract class CreationExtras {
    internal val map: MutableMap<Key<*>, Any?> = mutableMapOf()

    interface Key<T>

    abstract operator fun <T> get(key: Key<T>): T?

    object Empty : CreationExtras() {
        override fun <T> get(key: Key<T>): T? = null
    }
}

internal class MutableCreationExtras(initialExtras: CreationExtras) : CreationExtras() {

    init {
        map.putAll(initialExtras.map)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: Key<T>): T? =
        map[key] as T?

    operator fun <T> set(key: Key<T>, value: T) {
        map[key] = value
    }
}

internal fun mutableCreationExtrasOf(vararg pairs: Pair<CreationExtras.Key<*>, Any?>): MutableCreationExtras =
    MutableCreationExtras(CreationExtras.Empty).apply { map.putAll(pairs) }

internal interface ViewModelFactory<VM> {

    fun create(extras: CreationExtras): VM

    companion object {
        private object DependenciesKeyImpl : CreationExtras.Key<Dependencies>

        internal val DEPENDENCIES_KEY: CreationExtras.Key<Dependencies> = DependenciesKeyImpl
    }

}

@Composable
internal fun <VM> viewModel(
    factory: ViewModelFactory<VM>,
    extras: CreationExtras = CreationExtras.Empty
): VM {
    val allExtras = MutableCreationExtras(extras).apply {
        set(DEPENDENCIES_KEY, LocalDependencies.current)
    }
    return factory.create(allExtras)
}
