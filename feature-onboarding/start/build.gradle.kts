plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.start"
}

dependencies {
    implementation(projects.designsystem)


    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.join)
    implementation(projects.data.oauth)
    implementation(projects.common)
    implementation(projects.data.token)
    implementation(projects.data.core)
    implementation(projects.data.firebaseToken)


    implementation(libs.firebase.auth)
    implementation(libs.google.play.auth)

}