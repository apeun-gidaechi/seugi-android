plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.home"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)

    implementation(projects.designsystem)
    implementation(projects.data.workspace)
    implementation(projects.data.meal)
    implementation(projects.data.timetable)
    implementation(projects.common)
}