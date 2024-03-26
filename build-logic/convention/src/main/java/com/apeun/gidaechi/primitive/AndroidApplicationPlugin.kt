package com.apeun.gidaechi.primitive


import com.apeun.gidaechi.dsl.androidApplication
import com.apeun.gidaechi.dsl.setupAndroid
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