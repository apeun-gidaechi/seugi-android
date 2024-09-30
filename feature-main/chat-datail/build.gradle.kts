plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.chatdatail"
}

dependencies {

    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.stompClient)
    implementation(projects.data.message)
    implementation(projects.data.profile)
    implementation(projects.data.core)
    implementation(projects.data.token)
    implementation(projects.data.file)
    implementation(projects.data.perosnalChat)
    implementation(projects.data.groupChat)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.zoomable)
}