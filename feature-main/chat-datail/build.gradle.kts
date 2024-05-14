plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.chatdatail"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.chatDetail)
    implementation(libs.kotlinx.collections.immutable)
}