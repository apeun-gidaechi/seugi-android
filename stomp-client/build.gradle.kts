plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.hilt)
}

android {
    namespace = "com.seugi.stompclient"
}

dependencies {
    implementation("io.reactivex.rxjava2:rxjava:2.2.5")
    // Supported transports
    api("org.java-websocket:Java-WebSocket:1.3.6")
    api(libs.okhttp3)
//    api("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.android.support:support-annotations:28.0.0")
}