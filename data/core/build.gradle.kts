import com.apeun.gidaechi.dsl.android

plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    android {
        namespace = "com.apeun.gidaechi.data.core"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
}
