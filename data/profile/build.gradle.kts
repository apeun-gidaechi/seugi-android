plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.apeun.gidaechi.data.profile"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.profile)
    implementation(projects.network.core)
    implementation(projects.data.core)
}