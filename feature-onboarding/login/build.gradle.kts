plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.login"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.featureOnboarding.join)
    implementation(projects.data.member)
    implementation(projects.network.signIn)
    implementation(projects.common)
    implementation(projects.data.token)
    implementation(projects.data.firebaseToken)
}