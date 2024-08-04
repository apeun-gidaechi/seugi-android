plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.network.chatdetail"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)
    implementation(projects.stompClient)
    implementation(projects.data.token)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.rx)
}