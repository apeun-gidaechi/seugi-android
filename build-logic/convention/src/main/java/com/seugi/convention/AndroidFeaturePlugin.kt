package com.seugi.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.seugi.android")
                apply("com.seugi.android.kotlin")
                apply("com.seugi.android.compose")
                apply("com.seugi.android.hilt")
            }
        }
    }
}