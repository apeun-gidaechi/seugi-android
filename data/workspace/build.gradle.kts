plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.apeun.gidaechi.data.workspace"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.workspace)
    implementation(projects.network.core)
    implementation(projects.data.core)
    implementation(libs.kotlinx.coroutines.android)
}