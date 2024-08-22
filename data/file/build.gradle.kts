plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android)
}

android {
    namespace = "com.seugi.data.file"
}

dependencies {
    api(projects.network.file)
    implementation(projects.common)
    implementation(libs.kotlinx.coroutines.android)
}