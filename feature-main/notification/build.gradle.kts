plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.notification"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.core)
    implementation(projects.data.notification)
    implementation(projects.data.workspace)
    implementation("com.github.Abhimanyu14:compose-emoji-picker:1.0.0-alpha16")
}