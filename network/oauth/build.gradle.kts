plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
}

android {
    namespace = "com.seugi.network.oauth"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)
    implementation(libs.kotlinx.coroutines.android)
}