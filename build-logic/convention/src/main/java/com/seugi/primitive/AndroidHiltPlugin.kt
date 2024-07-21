package com.seugi.primitive

import com.seugi.dsl.android
import com.seugi.dsl.implementation
import com.seugi.dsl.ksp
import com.seugi.dsl.library
import com.seugi.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }
            android {
                packagingOptions {
                    resources {
                        excludes += "META-INF/gradle/incremental.annotation.processors"
                    }
                }
            }
            dependencies {
                implementation(libs.library("hilt-android"))
                ksp(libs.library("hilt-compiler"))
            }
        }
    }

}