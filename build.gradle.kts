import com.diffplug.gradle.spotless.SpotlessExtension


// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.dagger.hilt).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.spotless)
}
//
subprojects {
    plugins.apply(rootProject.libs.plugins.spotless.get().pluginId)

    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt", "**/test/**.kt", "**/androidTest/**.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            ktlint()
                .setEditorConfigPath("${project.rootDir}/spotless/.editorconfig")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}
