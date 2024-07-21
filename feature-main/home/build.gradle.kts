plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.home"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.designsystem)
}