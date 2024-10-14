package com.seugi.primitive


import com.seugi.dsl.androidApplication
import com.seugi.dsl.setupAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }
            androidApplication {
                setupAndroid()
            }
        }
    }

}