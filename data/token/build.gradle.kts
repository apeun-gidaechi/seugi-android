plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.apeun.gidaechi.data.token"
}

dependencies {

    implementation(projects.common)
    implementation(projects.data.core)
    implementation(projects.local.room)
}