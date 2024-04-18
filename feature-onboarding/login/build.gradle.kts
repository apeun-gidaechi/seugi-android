plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.login"
}

dependencies {
    implementation(projects.designsystem)

}