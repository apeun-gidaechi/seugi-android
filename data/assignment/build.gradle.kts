plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.data.task"
}

dependencies {

    implementation(projects.common)
    implementation(projects.network.core)
    implementation(projects.network.assignment)
    api(projects.data.core)
}