enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "seugi"
include(
    ":app",
    ":designsystem",
    ":designsystem-preview",
    ":network:core",
    ":common",
    ":feature-main:main",
    ":feature-main:chat",
    ":feature-main:chat-datail",
    ":feature-main:home"
)
include(":feature-main:room")
