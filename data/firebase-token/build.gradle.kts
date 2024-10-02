plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.data.firebasetoken"
}

dependencies {

    implementation(projects.common)
    implementation(projects.local.room)
}