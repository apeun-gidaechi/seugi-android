plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.main"
}

dependencies {
    implementation(projects.designsystem)

    implementation(projects.featureMain.chat)
    implementation(projects.featureMain.chatDatail)
    implementation(projects.featureMain.home)
    implementation(projects.featureMain.room)
    implementation(projects.featureMain.roomCreate)

    implementation(projects.featureOnboarding.onabording)
    implementation(projects.featureOnboarding.login)
    implementation(projects.featureOnboarding.join)
    implementation(projects.featureOnboarding.start)
}