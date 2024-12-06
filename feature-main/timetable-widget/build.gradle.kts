plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.timetable.widget"
}
dependencies {
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.timetable)
    implementation(projects.data.workspace)
}