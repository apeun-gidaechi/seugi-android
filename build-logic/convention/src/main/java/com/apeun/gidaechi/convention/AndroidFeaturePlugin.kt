package com.apeun.gidaechi.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.apeun.gidaechi.android")
                apply("com.apeun.gidaechi.android.kotlin")
                apply("com.apeun.gidaechi.android.compose")
                apply("com.apeun.gidaechi.android.hilt")
            }
        }
    }
}