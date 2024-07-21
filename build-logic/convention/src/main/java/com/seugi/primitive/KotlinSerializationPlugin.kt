package com.seugi.primitive

import com.seugi.dsl.implementation
import com.seugi.dsl.library
import com.seugi.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            dependencies {
                implementation(libs.library("kotlinx-serialization-json"))
            }
        }
    }
}