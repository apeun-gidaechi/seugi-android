plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)

}

android {
    namespace = "com.seugi.network.file"
}

dependencies {
    implementation(projects.common)
    api(projects.network.core)
}