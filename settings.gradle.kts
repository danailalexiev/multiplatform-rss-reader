pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        kotlin("android").version(extra["kotlin.version"] as String)
        id("com.android.application").version(extra["agp.version"] as String)
        id("com.android.library").version(extra["agp.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("arrow") {
            version("arrowVersion", "1.2.0")

            library("arrowCore", "io.arrow-kt", "arrow-core").versionRef("arrowVersion")
        }

        create("ktor") {
            version("ktorVersion", "2.3.4")

            library("ktorCore", "io.ktor", "ktor-client-core").versionRef("ktorVersion")
            library("ktorClientLog", "io.ktor", "ktor-client-logging").versionRef("ktorVersion")

            library("ktorClientJs", "io.ktor", "ktor-client-js").versionRef("ktorVersion")
            library("ktorClientOkHttp", "io.ktor", "ktor-client-okhttp").versionRef("ktorVersion")

            bundle("client", listOf("ktorCore", "ktorClientLog"))
        }

        create("kotlinx") {
            version("coroutinesVersion", "1.7.1")
            version("htmlVersion", "0.8.0")

            library(
                "coroutinesCore",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            ).versionRef("coroutinesVersion")

            library(
                "coroutinesCoreJs",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core-js"
            ).versionRef("coroutinesVersion")

            library(
                "coroutinesAndroid",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-android"
            ).versionRef("coroutinesVersion")

            library(
                "htmlJs",
                "org.jetbrains.kotlinx",
                "kotlinx-html-js"
            ).versionRef("htmlVersion")
        }

        create("androidx") {
            version("lifecycleVersion", "2.6.2")

            library(
                "viewModelCompose",
                "androidx.lifecycle",
                "lifecycle-viewmodel-compose"
            ).versionRef("lifecycleVersion")
            library(
                "lifecycleRuntimeCompose",
                "androidx.lifecycle",
                "lifecycle-runtime-compose"
            ).versionRef("lifecycleVersion")

            bundle("lifecycleCompose", listOf("lifecycleRuntimeCompose", "viewModelCompose"))
        }
    }
}

rootProject.name = "multiplatform-rss-reader"

include(":common", ":common-compose", ":web-js", ":web-compose", ":android")
