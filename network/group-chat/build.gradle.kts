plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.network.groupchat"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)
}