plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.onboarding"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.start)
    implementation(projects.featureOnboarding.join)

}