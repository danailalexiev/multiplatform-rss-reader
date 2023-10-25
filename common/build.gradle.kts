plugins {
    kotlin("multiplatform")
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
                implementation(ktor.bundles.client)
                api(arrow.arrowCore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting {
            dependencies {
                api(ktor.ktorClientJs)
            }
        }
        val jsTest by getting

        val androidMain by getting {
            dependencies {
                api(ktor.ktorClientOkHttp)
            }
        }
        val androidUnitTest by getting
    }
}

android {
    namespace = "bg.dalexiev.rss.common"
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
