plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.nav-host"
}

dependencies {
    implementation(projects.designsystem)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)

    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.join)


}