plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.network.schedule"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.core)
    api(projects.data.core)
}