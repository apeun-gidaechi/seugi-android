package com.apeun.gidaechi.primitive


import com.apeun.gidaechi.dsl.implementation
import com.apeun.gidaechi.dsl.ksp
import com.apeun.gidaechi.dsl.library
import com.apeun.gidaechi.dsl.libs
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