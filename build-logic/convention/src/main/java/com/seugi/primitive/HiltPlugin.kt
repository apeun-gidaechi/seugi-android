package com.seugi.primitive


import com.seugi.dsl.implementation
import com.seugi.dsl.ksp
import com.seugi.dsl.library
import com.seugi.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                implementation(libs.library("hilt-core"))
                ksp(libs.library("hilt-compiler"))
            }
        }
    }

}