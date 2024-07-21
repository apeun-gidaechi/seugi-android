plugins {
    alias(libs.plugins.seugi.android.application)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
//    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.seugi.designsystem.preview"
    defaultConfig {
        applicationId = "com.seugi.seugi"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":designsystem"))
    implementation(libs.rive)
}