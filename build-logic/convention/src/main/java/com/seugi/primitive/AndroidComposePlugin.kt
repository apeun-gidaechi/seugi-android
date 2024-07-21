package com.seugi.primitive

import com.seugi.dsl.android
import com.seugi.dsl.debugImplementation
import com.seugi.dsl.implementation
import com.seugi.dsl.implementationPlatform
import com.seugi.dsl.kotlinOptions
import com.seugi.dsl.library
import com.seugi.dsl.libs
import com.seugi.dsl.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val projectPath = rootProject.file(".").absolutePath
            android {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.version("androidx-compose-compiler")
                }
                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs + listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$projectPath/report/compose-metrics"
                    )
                    freeCompilerArgs = freeCompilerArgs + listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$projectPath/report/compose-reports"
                    )
                }
            }

            dependencies {
                implementation(libs.library("androidx-core"))
                implementationPlatform(libs.library("androidx-compose-bom"))
                implementation(libs.library("androidx-compose-activity"))
                implementation(libs.library("androidx-compose-hilt-navigation"))
                implementation(libs.library("androidx-compose-lifecycle"))
                implementation(libs.library("androidx-compose-navigation"))
                implementation(libs.library("androidx-compose-ui"))
                implementation(libs.library("androidx-compose-ui-foundation"))
                implementation(libs.library("androidx-compose-material3"))
                implementation(libs.library("androidx-lifecycle-runtime"))
                implementation(libs.library("androidx-compose-ui-tooling"))
                debugImplementation(libs.library("androidx-compose-ui-test-manifest"))
            }
        }
    }

}