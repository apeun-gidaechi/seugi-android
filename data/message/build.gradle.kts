plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.data.chatdetail"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.network.core)
    implementation(projects.network.message)
    implementation(projects.common)
}