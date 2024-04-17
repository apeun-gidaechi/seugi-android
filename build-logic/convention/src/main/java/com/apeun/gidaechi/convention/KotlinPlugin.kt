package com.apeun.gidaechi.convention

import com.apeun.gidaechi.dsl.implementation
import com.apeun.gidaechi.dsl.library
import com.apeun.gidaechi.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }
        dependencies {
            implementation(libs.library("kotlinx-coroutines-core"))
        }
    }
}