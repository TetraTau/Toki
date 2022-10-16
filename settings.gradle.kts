pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "tau-server"

include(
    "paper-api",
    "paper-server",
    "paper-mojang-api",
    "test-plugin"
)
