plugins {
    kotlin("multiplatform")
}

group = "bg.dalexiev.rss"
version = "1.0-SNAPSHOT"

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig(Action {
                cssSupport {
                    enabled.set(true)
                }
            })
        }
        binaries.executable()
    }

    sourceSets {

        val jsMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(kotlin("stdlib-js"))
                implementation(kotlinx.htmlJs)
                implementation(kotlinx.coroutinesCoreJs)
                implementation(arrow.arrowCore)

                implementation(npm("@material/circular-progress", "14.0.0"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

    }
}