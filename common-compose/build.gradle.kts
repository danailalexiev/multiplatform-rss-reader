plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "bg.dalexiev.rss"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    js(IR) {
        browser {
            commonWebpackConfig(Action {
                cssSupport {
                    enabled.set(true)
                }
            })
        }
    }

    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                api(project(":common"))

                implementation(kotlinx.coroutinesCore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlinx.coroutinesCoreJs)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(androidx.bundles.lifecycleCompose)
                implementation(kotlinx.coroutinesAndroid)
            }
        }
    }
}

android {
    namespace = "bg.dalexiev.rss.common.compose"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}