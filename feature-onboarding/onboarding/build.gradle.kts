plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.onboarding"
}

dependencies {
    implementation(projects.designsystem)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)

}