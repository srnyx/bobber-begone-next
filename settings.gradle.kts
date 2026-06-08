pluginManagement {
    repositories {
        maven("https://maven.kikugie.dev/releases/")
        maven("https://maven.kikugie.dev/snapshots/")
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.5"
    id("dev.kikugie.loom-back-compat") version "0.3"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "BobberBegone"

stonecutter {
    create(rootProject) {
        versions("1.21.2")
        versions("26.1")
    }
}
