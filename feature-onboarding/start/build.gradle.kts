plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.start"
}

dependencies {
    implementation(projects.designsystem)


    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.join)



}