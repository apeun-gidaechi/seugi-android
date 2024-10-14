plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.room"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.core)
    implementation(projects.data.groupChat)
}