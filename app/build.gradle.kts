//import com.seugi.dsl.androidTestImplementation

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.seugi.android.application)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
    alias(libs.plugins.seugi.android.hilt)
}

android {
    namespace = "com.seugi"

    defaultConfig {
        applicationId = "com.seugi"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(projects.network.core)
    implementation(projects.designsystem)
    implementation(projects.featureMain.main)
    implementation(projects.featureOnboarding.onboarding)
}