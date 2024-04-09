plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.main"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.featureMain.chat)
}