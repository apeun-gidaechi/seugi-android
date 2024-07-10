plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.apeun.gidaechi.local.room"
}

dependencies{
    val room_version = libs.versions.room.get()
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")


}