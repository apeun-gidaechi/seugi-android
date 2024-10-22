plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.timetable"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.timetable)
    implementation(libs.kotlinx.datetime)
}