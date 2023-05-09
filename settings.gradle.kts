pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://mvn.tetratau.net/releases/")
        maven("https://maven.minecraftforge.net/releases/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://maven.fabricmc.net")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "toki"

include(
    /*
    "paper-api",
    "paper-server",
    "paper-mojang-api",
     */
    "fabric-loader",
    "fabric-loader:minecraft",
    "fabric-loader:junit",
    "paperclip",
    "paperclip:java6",
    "paperclip:java17"
)

