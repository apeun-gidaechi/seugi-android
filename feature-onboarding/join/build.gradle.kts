plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.seugi.join"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.member)
    implementation(projects.data.workspace)
    implementation(projects.common)
    implementation(projects.ui)

    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
}