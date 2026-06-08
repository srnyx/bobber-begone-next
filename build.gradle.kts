import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addReplacementsTask
import xyz.srnyx.gradlegalaxy.utility.setupJava

plugins {
    id("dev.kikugie.loom-back-compat")
    id("xyz.srnyx.gradle-galaxy") version "3.0.1"
}

val is261Plus: Boolean = sc.eval(sc.current.version, ">=26.1")
val java = if (is261Plus) JavaVersion.VERSION_25 else JavaVersion.VERSION_21
val modId = property("mod.id").toString()
val modVersion = property("mod.version").toString()
val loaderVersion = property("loader.version").toString()

setupJava(JavaSetupConfig(
    group = property("mod.group").toString(),
    version = "$modVersion-${sc.current.version}",
    description = "A simple Fabric client mod that removes the bobber when hooked (updated from Bobber Begone)",
    javaVersion = java))

repository(Repository.FABRIC, Repository.MAVEN_CENTRAL)

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    // Mappings
    if (is261Plus) {
        loomx.applyMojangMappings()
    } else {
        mappings("net.fabricmc:yarn:${property("yarn_mappings").toString()}:v2")
    }
}

base.archivesName = modId

// Replacements for fabric.mod.json
addReplacementsTask(setOf("fabric.mod.json", "bobberbegone.mixins.json"), mapOf(
    "mod_id" to modId,
    "name" to property("mod.name").toString(),
    "version" to modVersion,
    "loader_version" to loaderVersion,
    "minecraft_compat" to property("mod.mc.compat").toString(),
    "java" to if (java == JavaVersion.VERSION_25) "JAVA_25" else "JAVA_21"))

// Package LICENSE file into jar
tasks.jar { from("LICENSE") { rename { "${it}_$name"} } }

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json") // Useful for interface injection

    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        jvmArguments.add("-Dmixin.debug.export=true") // Exports transformed classes for debugging
        runDirectory.set(file("../../run")) // Shares the run directory between versions
    }
}

tasks {
    // Builds the version into a shared folder in `build/libs`
    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod and copies the jar to a shared folder"

        // loomx.modJar returns the jar task for the applied loom variant
        from(loomx.modJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs"))

        dependsOn("build")
    }
}
