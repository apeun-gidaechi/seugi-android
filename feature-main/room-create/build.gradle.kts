plugins {
    alias(libs.plugins.seugi.android.feature)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.apeun.gidaechi.roomcreate"
}
dependencies {
    implementation(libs.kotlinx.collections.immutable)

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.core)
    implementation(projects.data.workspace)
    implementation(projects.network.core)
    implementation(projects.network.workspace)
}