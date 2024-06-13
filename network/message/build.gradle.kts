plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.network.chatdetail"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)
    implementation(libs.stomp.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.rx)
}