import com.seugi.dsl.android

plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
}

android {
    android {
        namespace = "com.seugi.data.core"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)
    implementation(projects.local.room)
    implementation(projects.network.meal)

    implementation(libs.kotlinx.collections.immutable)
    implementation(project(":local:room"))
}
