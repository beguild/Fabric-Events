plugins {
    java
    id("java-library")
    id("io.freefair.lombok") version "8.10.2"
    id("fabric-loom") version "1.7-SNAPSHOT"
}

group = "dev.frydae"
version = "${property("fabric_version")!!}-SNAPSHOT"

apply(from = uri("https://files.frydae.dev/gradle/publishing.gradle"))

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    modImplementation("dev.frydae:fcs-fabric:${version}")
    implementation(group = "com.google.errorprone", name = "error_prone_annotations", version = "2.29.2")

}

repositories {
    maven { url = uri("https://maven.frydae.dev/releases/") }
    maven { url = uri("https://maven.frydae.dev/snapshots/") }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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