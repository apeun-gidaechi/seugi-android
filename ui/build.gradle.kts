plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
}

android {
    namespace = "com.apeun.gidaechi.ui"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}