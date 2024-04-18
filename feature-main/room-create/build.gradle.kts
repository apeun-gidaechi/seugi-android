plugins {
    alias(libs.plugins.seugi.android.feature)
}

android {
    namespace = "com.apeun.gidaechi.roomcreate"
}
dependencies {

    implementation(projects.designsystem)
}