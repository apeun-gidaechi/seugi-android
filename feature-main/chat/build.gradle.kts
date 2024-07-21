plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.chat"
}

dependencies {

    implementation(projects.designsystem)
    implementation(libs.kotlinx.collections.immutable)
    implementation(projects.data.perosnalChat)
    implementation(projects.common)
    implementation(projects.data.core)
}