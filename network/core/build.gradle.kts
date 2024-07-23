import java.util.Properties

plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.apeun.gidaechi.network.core"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "${properties["BASE_URL"]}")
        buildConfigField("String", "WS_URL", "${properties["WS_URL"]}")

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
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
    implementation(libs.okhttp3.logging.interceptor)
    implementation(projects.data.token)

    implementation(libs.stomp.android)
}