plugins {
    java
    id("java-library")
    id("io.freefair.lombok") version "8.7.1"
    id("fabric-loom") version "1.7-SNAPSHOT"
}

group = "dev.frydae"
version = "${property("fabric_version")!!}-SNAPSHOT"

apply(from = uri("https://files.frydae.dev/gradle/common.gradle"))
apply(from = uri("https://files.frydae.dev/gradle/publishing.gradle"))

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    modImplementation("dev.frydae:fcs-fabric:${version}")

    listOf("BeGuild-Common").forEach { dep ->
        if (projectDir.parentFile.listFiles()?.any { it.isDirectory && it.name.equals(dep) } == true) {
            implementation(project(path = ":${dep}", configuration = "namedElements"))
        } else {
            implementation("dev.frydae:${dep.lowercase()}:${version}")
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf(
                    "version" to project.version
            ))
        }
    }

    test {
        useJUnitPlatform()
    }

    jar {
        from("LICENSE")
    }
}