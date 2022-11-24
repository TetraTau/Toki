pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net")
    }
}

rootProject.name = "papyrus"

include(
    "paper-api",
    "paper-server",
    "paper-mojang-api",
    "test-plugin",
    "fabric-loader",
    "fabric-loader:minecraft",
    "paperclip",
    "paperclip:java6",
    "paperclip:java17"
)

if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    include("fabric-loader:minecraft:minecraft-test")
} else {
    println("Minecraft test sub project requires java 17 or higher!")
}
