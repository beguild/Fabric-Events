plugins {
    java
    id("java-library")
    id("fabric-loom") version "1.4-SNAPSHOT"
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

    if (projectDir.parentFile.name.equals("BeGuild")) {
        implementation(project(path = ":BeGuild-Common", configuration = "namedElements"))
    } else {
        implementation("dev.frydae:beguild-common:${version}")?.let { include(it) }
    }
}

loom {
    accessWidenerPath = file("src/main/resources/fabric-events.accesswidener")
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