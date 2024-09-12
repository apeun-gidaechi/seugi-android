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
    implementation(libs.compose.emoji.picker)
}