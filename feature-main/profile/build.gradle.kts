plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.profile"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
}