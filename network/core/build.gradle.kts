plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.network.core"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

    api(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)
    api(libs.ktor.serialization.kotlinx.gson)
    api(libs.ktor.client.content.negotiation)
    api(libs.kotlinx.datetime)
    implementation(projects.common)
    implementation(libs.gson)

}