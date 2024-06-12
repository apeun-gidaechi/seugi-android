import java.net.URI



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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "seugi"
include(
    ":app",
    ":designsystem",
    ":designsystem-preview",
    ":network:core",
    ":network:message",
    ":network:profile",
    ":network:sign-in",
    ":common",
    ":data:email-sign-in",
    ":feature-main:main",
    ":feature-main:chat",
    ":feature-main:chat-datail",
    ":feature-main:home",
    ":feature-main:room",
    ":feature-main:room-create",
    ":feature-onboarding:login",
    ":feature-onboarding:join",
    ":data:message",
    ":data:profile"
    ":feature-onboarding:join",
    ":feature-onboarding:start",
    ":feature-onboarding:onabording"
)