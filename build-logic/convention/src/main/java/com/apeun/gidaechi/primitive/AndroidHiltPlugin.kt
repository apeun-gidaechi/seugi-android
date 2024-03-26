package com.apeun.gidaechi.primitive

import com.apeun.gidaechi.dsl.android
import com.apeun.gidaechi.dsl.implementation
import com.apeun.gidaechi.dsl.ksp
import com.apeun.gidaechi.dsl.library
import com.apeun.gidaechi.dsl.libs
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