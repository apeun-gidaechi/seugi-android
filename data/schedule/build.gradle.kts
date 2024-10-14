plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.data.schedule"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.core)
    implementation(projects.network.schedule)
    api(projects.data.core)
}