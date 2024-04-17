import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.apeun.gidaechi.seugi.build_logic"


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
            id = "com.apeun.gidaechi.android.application"
            implementationClass= "com.apeun.gidaechi.primitive.AndroidApplicationPlugin"
        }
        register("androidCompose") {
            id = "com.apeun.gidaechi.android.compose"
            implementationClass = "com.apeun.gidaechi.primitive.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "com.apeun.gidaechi.android.hilt"
            implementationClass = "com.apeun.gidaechi.primitive.AndroidHiltPlugin"
        }
        register("androidKotlin") {
            id = "com.apeun.gidaechi.android.kotlin"
            implementationClass = "com.apeun.gidaechi.primitive.AndroidKotlinPlugin"
        }
        register("android") {
            id = "com.apeun.gidaechi.android"
            implementationClass = "com.apeun.gidaechi.primitive.AndroidPlugin"
        }
        register("hilt") {
            id = "com.apeun.gidaechi.hilt"
            implementationClass = "com.apeun.gidaechi.primitive.HiltPlugin"
        }
        register("kotlinSerialization") {
            id = "com.apeun.gidaechi.kotlin.serialization"
            implementationClass = "com.apeun.gidaechi.primitive.KotlinSerializationPlugin"
        }

        //convention
        register("androidFeature") {
            id = "com.apeun.gidaechi.android.feature"
            implementationClass = "com.apeun.gidaechi.convention.AndroidFeaturePlugin"
        }
        register("kotlin") {
            id = "com.apeun.gidaechi.convention.kotlin"
            implementationClass = "com.apeun.gidaechi.convention.KotlinPlugin"
        }
    }
}