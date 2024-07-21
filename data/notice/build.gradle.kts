plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.data.notice"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.network.core)
    implementation(projects.network.notice)
    implementation(projects.data.core)
    implementation(projects.common)
}