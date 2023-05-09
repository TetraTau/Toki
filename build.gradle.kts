import io.papermc.paperweight.patcher.tasks.CheckoutRepo
import io.papermc.paperweight.tasks.ApplyGitPatches
import io.papermc.paperweight.tasks.RebuildGitPatches
import io.papermc.paperweight.util.cacheDir

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("net.tetratau.tokimak.core") version "0.1.1-SNAPSHOT"
}

val paperMavenPublicUrl = "https://papermc.io/repo/repository/maven-public/"

repositories {
    mavenCentral()
    mavenLocal()
    maven(paperMavenPublicUrl)
}

dependencies {
    paperclip(project(":paperclip"))
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}



tasks {
    named("createPaperclipJar") {
        group = "toki"
        dependsOn(project(":paperclip").tasks.named("jar"))
    }

    // TODO fancy patch configuration in Tokimak Core?
    fun createPatchTasks(repoUrl: String, projectName: String, patchDirName: String, taskName: String, refPropertyName: String) {
        val projPatchDir = layout.projectDirectory.dir("patches/$patchDirName")
        val projectDir = layout.projectDirectory.dir(projectName.toLowerCase())
        val refCommit = providers.gradleProperty(refPropertyName).get()

        val checkOutRepoTask = register<CheckoutRepo>("clone$taskName") {
            group = "toki"
            repoName.set(patchDirName)
            url.set(repoUrl)
            ref.set(refCommit)
            workDir.set(layout.cacheDir(io.papermc.paperweight.util.constants.UPSTREAMS))
        }

        register<ApplyGitPatches>("apply${taskName}Patches") {
            dependsOn(checkOutRepoTask)
            group = "toki"
            // bareDirectory.set(true) // Set this to true because without the git recreation it won't work for some reason.
            unneededFiles.set(listOf("settings.gradle.kts", "README.md"))
            upstreamBranch.set("master")
            branch.set("master")
            upstream.set(checkOutRepoTask.get().outputDir)
            patchDir.set(projPatchDir)
            outputDir.set(projectDir)
        }

        register<RebuildGitPatches>("rebuild${taskName}Patches") {
            group = "toki"
            patchDir.set(projPatchDir)
            inputDir.set(projectDir)

            baseRef.set("base")
        }
    }

    createPatchTasks(github("FabricMC", "fabric-loader"), "fabric-loader", "fabricloader", "FabricLoader", "fabricloaderRef")
    createPatchTasks(github("PaperMC", "Paperclip"), "Paperclip", "paperclip", "Paperclip", "paperclipRef")
}

fun github(owner: String, repo: String): String {
    return "https://github.com/$owner/$repo.git"
}

