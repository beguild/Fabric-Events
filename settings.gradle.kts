pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.15"
}

val isCI = System.getenv("CI") != null

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        isUploadInBackground = !isCI
        publishAlways()

        capture {
            isTaskInputFiles = true
        }
    }
}

rootProject.name = "Fabric-Events"

listOf("BeGuild-Common").forEach { dep ->
    projectDir.parentFile.listFiles()?.filter { it.isDirectory && it.name.equals(dep) }?.forEach {
        include(dep)

        project(":${dep}").projectDir = it
    }
}