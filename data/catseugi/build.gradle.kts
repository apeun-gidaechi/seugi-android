plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.data.catseugi"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.network.catseugi)
    implementation(projects.data.core)
    implementation(projects.common)
}