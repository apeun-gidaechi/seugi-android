plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.apeun.gidaechi.local.room"
}

dependencies{
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")


}