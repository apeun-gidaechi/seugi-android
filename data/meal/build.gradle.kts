plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.kotlin)

}

android {
    namespace = "com.seugi.data.meal"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.network.meal)
    implementation(projects.local.room)
    implementation(projects.common)
}