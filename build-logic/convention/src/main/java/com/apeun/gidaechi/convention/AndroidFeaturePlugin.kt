package com.apeun.gidaechi.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.apeun.gidaechi.primitive.android")
//                apply("com.apeun.gidaechi.primitive.android.kotlin")
                apply("com.apeun.gidaechi.primitive.android.compose")
//                apply("b1nd.dodam.primitive.android.hilt")
            }
        }
    }
}