plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.onboarding"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.start)
    implementation(projects.featureOnboarding.join)

}