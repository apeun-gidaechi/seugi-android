import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.seugi.build_logic"


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()


// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}
dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.seugi.android.application"
            implementationClass= "com.seugi.primitive.AndroidApplicationPlugin"
        }
        register("androidCompose") {
            id = "com.seugi.android.compose"
            implementationClass = "com.seugi.primitive.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "com.seugi.android.hilt"
            implementationClass = "com.seugi.primitive.AndroidHiltPlugin"
        }
        register("androidKotlin") {
            id = "com.seugi.android.kotlin"
            implementationClass = "com.seugi.primitive.AndroidKotlinPlugin"
        }
        register("android") {
            id = "com.seugi.android"
            implementationClass = "com.seugi.primitive.AndroidPlugin"
        }
        register("hilt") {
            id = "com.seugi.hilt"
            implementationClass = "com.seugi.primitive.HiltPlugin"
        }
        register("kotlinSerialization") {
            id = "com.seugi.kotlin.serialization"
            implementationClass = "com.seugi.primitive.KotlinSerializationPlugin"
        }

        //convention
        register("androidFeature") {
            id = "com.seugi.android.feature"
            implementationClass = "com.seugi.convention.AndroidFeaturePlugin"
        }
        register("kotlin") {
            id = "com.seugi.convention.kotlin"
            implementationClass = "com.seugi.convention.KotlinPlugin"
        }
    }
}