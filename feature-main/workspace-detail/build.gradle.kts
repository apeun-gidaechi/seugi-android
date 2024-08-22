plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.workspacecreate"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.member)
    implementation(projects.data.workspace)
    implementation(projects.common)
    implementation(projects.ui)
}