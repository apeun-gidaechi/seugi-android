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


    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

}