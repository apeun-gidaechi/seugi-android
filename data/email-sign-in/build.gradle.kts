plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.data.emailsignin"
}

dependencies {
    implementation(projects.network.signIn)
    implementation(projects.network.core)
    implementation(projects.common)
    implementation(libs.kotlinx.coroutines.android)
}