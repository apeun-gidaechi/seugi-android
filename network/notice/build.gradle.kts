plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.network.notice"
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)
}