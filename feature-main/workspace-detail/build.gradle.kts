plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.workspacecdetail"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.member)
    implementation(projects.data.profile)
    implementation(projects.data.workspace)
    implementation(projects.common)
    implementation(projects.ui)
}