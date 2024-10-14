plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.network.personalchat"
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)
}