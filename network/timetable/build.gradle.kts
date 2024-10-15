plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
}

android {
    namespace = "com.seugi.network.timetable"
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.core)

    androidTestImplementation(libs.kotlinx.serialization.json)
    androidTestImplementation(libs.ktor.client.mock)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
//    androidTestImplementation(libs.testng)
}