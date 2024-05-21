import java.util.Properties

plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
}


val prperties = Properties()
prperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.apeun.gidaechi.network.core"

    defaultConfig {
        buildConfigField("String", "SERVER_URL", "${prperties["SERVER_URL"]}")

        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    api(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.kotlinx.datetime)
    implementation(projects.common)
}