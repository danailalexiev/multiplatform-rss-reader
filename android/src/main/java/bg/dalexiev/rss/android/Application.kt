package bg.dalexiev.rss.android

import android.app.Application
import bg.dalexiev.rss.common.Dependencies
import bg.dalexiev.rss.common.compose.DependenciesProvider
import bg.dalexiev.rss.common.dependencies

class Application: Application(), DependenciesProvider {

    override val dependencies: Dependencies = dependencies(true)

}