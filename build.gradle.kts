import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addReplacementsTask
import xyz.srnyx.gradlegalaxy.utility.getDefaultReplacements
import xyz.srnyx.gradlegalaxy.utility.setupJava

plugins {
    java
    id("fabric-loom") version "1.11-SNAPSHOT"
    id("xyz.srnyx.gradle-galaxy") version "2.0.2"
}

val loaderVersion = property("loader_version").toString()
val minecraftVersion = property("minecraft_version").toString()

setupJava(JavaSetupConfig(
    group = property("maven_group").toString(),
    version = property("mod_version").toString(),
    description = "A simple Fabric client mod that removes the bobber when hooked (updated from Bobber Begone)",
    javaVersion = JavaVersion.VERSION_21))

repository(Repository.FABRIC, Repository.MAVEN_CENTRAL)

dependencies {
    minecraft("com.mojang", "minecraft", minecraftVersion)
    mappings("net.fabricmc", "yarn", property("yarn_mappings").toString())
    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)
}

base.archivesName = name

// Replacements for fabric.mod.json
addReplacementsTask(setOf("fabric.mod.json"), getDefaultReplacements() + mapOf(
    "mod_id" to property("mod_id").toString(),
    "loader_version" to loaderVersion,
    "minecraft_version" to minecraftVersion))

// Package LICENSE file into jar
tasks.jar { from("LICENSE") { rename { "${it}_$name"} } }
