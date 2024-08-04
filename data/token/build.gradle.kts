plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.data.token"
}

dependencies {

    implementation(projects.common)
    implementation(projects.local.room)
    implementation(projects.network.token)
}