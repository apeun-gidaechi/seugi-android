plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.data.timetable"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.core)
    implementation(projects.network.timetable)
    api(projects.data.core)
}