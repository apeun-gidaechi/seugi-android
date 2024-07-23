plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
}

android {
    namespace = "com.seugi.designsystem"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
dependencies {
    api(libs.androidx.compose.material)
    implementation(libs.coil.compose)
    implementation(libs.rive)
}