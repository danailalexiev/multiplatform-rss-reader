plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "bg.dalexiev.rss"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":common-compose"))

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
}

android {
    namespace = "bg.dalexiev.rss.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "bg.dalexiev.rss.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}