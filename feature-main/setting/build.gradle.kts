plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.setting"
}
dependencies {
    implementation(libs.kotlinx.collections.immutable)

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.profile)
    implementation(projects.data.token)
    implementation(projects.data.member)
    implementation(projects.data.workspace)
    implementation(projects.data.file)

}