plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.notificationcreate"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.notification)
    implementation(projects.data.core)
}