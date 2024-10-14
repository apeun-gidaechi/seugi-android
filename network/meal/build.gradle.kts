plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.network.meal"
}

dependencies {
    api(projects.network.core)
    implementation(projects.common)
}