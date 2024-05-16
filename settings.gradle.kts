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
    ":network:sign-in",
    ":common",
    ":data",
    ":data:email-sign-in",
    ":feature-main:main",
    ":feature-main:chat",
    ":feature-main:chat-datail",
    ":feature-main:home",
    ":feature-main:room",
    ":feature-main:room-create",
    ":feature-onboarding:login",
    ":feature-onboarding:join",
    ":feature-onboarding:start",
    ":feature-onboarding:onabording"
)
