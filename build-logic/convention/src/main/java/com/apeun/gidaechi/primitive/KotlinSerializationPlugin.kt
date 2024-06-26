package com.apeun.gidaechi.primitive

import com.apeun.gidaechi.dsl.implementation
import com.apeun.gidaechi.dsl.library
import com.apeun.gidaechi.dsl.libs
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