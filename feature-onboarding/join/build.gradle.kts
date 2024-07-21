plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.join"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.emailSignIn)
    implementation(projects.data.workspace)
    implementation(projects.common)
}