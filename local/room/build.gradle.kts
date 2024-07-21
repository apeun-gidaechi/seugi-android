plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.seugi.local.room"
}

dependencies{
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)


}