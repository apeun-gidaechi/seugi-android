plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.join"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.emailSignIn)
    implementation(projects.common)
}