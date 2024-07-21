plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.network.groupchat"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)
}