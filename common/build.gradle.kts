plugins {
    alias(libs.plugins.seugi.kotlin)
    alias(libs.plugins.seugi.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
}


dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.collections.immutable)
}