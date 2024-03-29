plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
}

android {
    namespace = "com.apeun.gidaechi.designsystem"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
dependencies {
    api(libs.androidx.compose.material)
    implementation(libs.rive)
}