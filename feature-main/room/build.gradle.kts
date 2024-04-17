plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.room"
}

dependencies {

    implementation(projects.designsystem)
}