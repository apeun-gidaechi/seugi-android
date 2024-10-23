plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.meal"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.common)
    implementation(projects.data.meal)

}