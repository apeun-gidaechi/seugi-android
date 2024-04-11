plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.home"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.designsystem)
}