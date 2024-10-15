//import com.seugi.dsl.androidTestImplementation

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.seugi.android.application)
    alias(libs.plugins.seugi.android.kotlin)
    alias(libs.plugins.seugi.android.compose)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.seugi"

    defaultConfig {
        applicationId = "com.seugi"
        versionCode = 1
        versionName = "0.0.1"

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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.message)


    implementation(projects.network.core)
    implementation(projects.data.core)
    implementation(projects.designsystem)
    implementation(projects.featureMain.main)
    implementation(projects.featureOnboarding.onboarding)
    implementation(projects.common)
    implementation(projects.data.token)
    implementation(projects.data.firebaseToken)
    implementation(projects.featureMain.mealWidget)
    implementation(projects.featureMain.timetableWidget)

}