plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi.data.chatdetail"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.network.core)
    implementation(projects.network.message)
    implementation(projects.data.core)
    implementation(projects.network.meal)
    implementation(projects.network.notification)
    implementation(projects.common)
    implementation(projects.local.room)
}