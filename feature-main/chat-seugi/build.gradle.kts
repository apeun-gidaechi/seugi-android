plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.chatseugi"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.message)
    implementation(projects.data.profile)
    implementation(projects.data.core)
    implementation(libs.kotlinx.collections.immutable)
}