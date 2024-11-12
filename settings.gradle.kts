import java.net.URI

include(":data:oauth")


include(":network:oauth")


include(":data:firebase-token")


include(":data:file")


include(":network:file")


include(":feature-main:workspace-create")


include(":feature-main:workspace-detail")


include(":network:token")


include(":stomp-client")


include(":feature-main:workspace")


include(":data:notification")


include(":network:notification")


include(":feature-main:chat-seugi")


include(":data:token")


include(":local:room")


include(":feature-main:profile")


include(":feature-main:notification")


include(":data:group-chat")


include(":network:group-chat")


include(":data:core")


include(":network:personal-chat")


include(":data:perosnal-chat")




enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
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
    ":network:workspace",
    ":network:meal",
    ":network:timetable",
    ":network:catseugi",
    ":network:schedule",
    ":network:assignment",
    ":common",
    ":data:member",
    ":data:workspace",
    ":feature-main:main",
    ":feature-main:chat",
    ":feature-main:chat-datail",
    ":feature-main:home",
    ":feature-main:room",
    ":feature-main:room-create",
    ":feature-onboarding:login",
    ":feature-onboarding:join",
    ":data:message",
    ":data:profile",
    ":data:meal",
    ":data:timetable",
    ":data:catseugi",
    ":data:schedule",
    ":data:assignment",
    ":feature-onboarding:join",
    ":feature-onboarding:start",
    ":feature-onboarding:onboarding",
    ":feature-main:notification-create",
    ":feature-main:notification-edit",
    ":feature-main:timetable",
    ":feature-main:setting",
    ":feature-main:meal-widget",
    ":feature-main:meal",
    ":feature-main:timetable-widget",
    ":feature-main:assignment",
    ":feature-main:assignment-create",
    ":ui"
)