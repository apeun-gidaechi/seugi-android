plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.apeun.gidaechi.local.room"
}

dependencies{
    implementation ("androidx.room:room-runtime:2.2.6")
    annotationProcessor ("androidx.room:room-compiler:2.2.6")
}