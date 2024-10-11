plugins {
    alias(libs.plugins.seugi.android)
    alias(libs.plugins.seugi.android.hilt)
    alias(libs.plugins.seugi.kotlin.serialization)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.seugi.android.kotlin)
}

android {
    namespace = "com.seugi.meal.widget"
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.common)
    implementation(projects.data.meal)
    implementation(projects.data.workspace)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.work)
    implementation(libs.androidx.core.ktx)
}