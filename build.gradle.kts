import io.papermc.paperweight.patcher.tasks.CheckoutRepo
import io.papermc.paperweight.patcher.tasks.SimpleApplyGitPatches
import io.papermc.paperweight.patcher.tasks.SimpleRebuildGitPatches
import io.papermc.paperweight.util.cacheDir
import io.papermc.paperweight.util.constants.PAPERCLIP_CONFIG

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.papermc.paperweight.patcher") version "1.3.11"
}

//apply<net.tetratau.papyrus.PapyrusPlugin>()

val paperMavenPublicUrl = "https://papermc.io/repo/repository/maven-public/"

repositories {
    mavenCentral()
    mavenLocal()
    maven(paperMavenPublicUrl) {
        content { onlyForConfigurations(PAPERCLIP_CONFIG) }
    }
}

dependencies { // Daily reminder to update remapper, decompiler and paperclip if needed.
    remapper("net.fabricmc:tiny-remapper:0.8.2:fat")
    decompiler("net.minecraftforge:forgeflower:1.5.605.7")
    paperclip("io.papermc:paperclip:3.0.2")
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

val fabricLoaderRefCommit = providers.gradleProperty("fabricloaderRef").get()

paperweight {
    serverProject.set(project(":paper-server"))
    remapRepo.set(paperMavenPublicUrl)
    decompileRepo.set(paperMavenPublicUrl)

    usePaperUpstream(providers.gradleProperty("paperRef")) {
        withPaperPatcher {
            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("paper-api"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("paper-server"))
        }
        patchTasks {
            register("mojangApi") {
                isBareDirectory.set(true)
                upstreamDirPath.set("Paper-MojangAPI")
                patchDir.set(layout.projectDirectory.dir("patches/mojangapi"))
                outputDir.set(layout.projectDirectory.dir("paper-mojang-api"))
            }
        }
    }
// An alternative solution which looks better but doesn't work.
/*
    useStandardUpstream("fabricloader") {
        url.set(github("FabricMC", "fabric-loader"))
        ref.set(fabricLoaderRefCommit)
        useForUpstreamData.set(false)
        patchTasks {
            register("fabricloader") {
                isBareDirectory.set(false)
                upstreamDirPath.set("/")
                patchDir.set(layout.projectDirectory.dir("patches/fabricloader"))
                outputDir.set(layout.projectDirectory.dir("fabric-loader"))
            }
        }
    }
 */
}

tasks {
    generateDevelopmentBundle {
        apiCoordinates.set("net.tetratau.papyrus:paper-api")
        mojangApiCoordinates.set("net.tetratau.papyrus:mojang-api")
        libraryRepositories.set(
            listOf(
                "https://repo.maven.apache.org/maven2/",
                "https://libraries.minecraft.net/",
                "https://papermc.io/repo/repository/maven-public/",
                "https://maven.quiltmc.org/repository/release/",
                // "https://my.repo/", // This should be a repo hosting your API (in this example, 'com.example.paperfork:forktest-api')
            )
        )
    }

    fun createPatchTasks(orgName: String, projectName: String, patchDirName: String, taskName: String, refPropertyName: String) {
        val projPatchDir = layout.projectDirectory.dir("patches/$patchDirName")
        val projectDir = layout.projectDirectory.dir(projectName.toLowerCase())
        val refCommit = providers.gradleProperty(refPropertyName).get()

        val applyPaperServerPatchTask = getByName<SimpleApplyGitPatches>("applyServerPatches")

        val checkOutRepoTask = register<CheckoutRepo>("clone$taskName") {
            group = "papyrus"
            repoName.set(patchDirName)
            url.set(github(orgName, projectName))
            ref.set(refCommit)
            workDir.set(layout.cacheDir(io.papermc.paperweight.util.constants.UPSTREAMS))
        }

        register<SimpleApplyGitPatches>("apply${taskName}Patches") {
            dependsOn(checkOutRepoTask)
            group = "papyrus"
            bareDirectory.set(true) // Set this to true because without the git recreation it won't work for some reason.
            devImports.set(paperweight.devImports)
            upstreamBranch.set("master")
            mcDevSources.set(paperweight.mcDevSourceDir)
            sourceMcDevJar.set(applyPaperServerPatchTask.sourceMcDevJar)
            upstreamDir.set(checkOutRepoTask.get().outputDir)
            patchDir.set(projPatchDir)
            outputDir.set(projectDir)
        }

        register<SimpleRebuildGitPatches>("rebuild${taskName}Patches") {
            group = "papyrus"
            patchDir.set(projPatchDir)
            inputDir.set(projectDir)

            baseRef.set("base")
        }
    }

    createPatchTasks("FabricMC", "fabric-loader", "fabricloader", "FabricLoader", "fabricloaderRef")
    createPatchTasks("PaperMC", "Paperclip", "paperclip", "Paperclip", "paperclipRef")

    // FABRIC LOADER PATCH SYSTEM
    /*
    val fabricPatchDir = layout.projectDirectory.dir("patches/fabricloader")
    val fabricProjectDir = layout.projectDirectory.dir("fabric-loader")
    val fabricRefCommit = providers.gradleProperty("fabricloaderRef").get()

    val applyPaperServerPatchTask = getByName<SimpleApplyGitPatches>("applyServerPatches")

    val checkOutRepoTask = register<CheckoutRepo>("cloneFabricLoader") {
        group = "papyrus"
        repoName.set("fabricloader")
        url.set(github("FabricMC", "fabric-loader"))
        ref.set(fabricRefCommit)
        workDir.set(layout.cacheDir(io.papermc.paperweight.util.constants.UPSTREAMS))
    }

    register<SimpleApplyGitPatches>("applyFabricLoaderPatches") {
        dependsOn(checkOutRepoTask)
        group = "papyrus"
        bareDirectory.set(true) // Set this to true because without the git recreation it won't work for some reason.
        devImports.set(paperweight.devImports)
        upstreamBranch.set("master")
        mcDevSources.set(paperweight.mcDevSourceDir)
        sourceMcDevJar.set(applyPaperServerPatchTask.sourceMcDevJar)
        upstreamDir.set(checkOutRepoTask.get().outputDir)
        patchDir.set(fabricPatchDir)
        outputDir.set(fabricProjectDir)
    }

    register<SimpleRebuildGitPatches>("rebuildFabricLoaderPatches") {
        group = "papyrus"
        patchDir.set(fabricPatchDir)
        inputDir.set(fabricProjectDir)

        baseRef.set(fabricRefCommit)
    }

     */

    // PAPERCLIP PATCH SYSTEM


}

fun github(owner: String, repo: String): String {
    return "https://github.com/$owner/$repo.git"
}

allprojects {
    // Publishing API:
    // ./gradlew :ForkTest-API:publish[ToMavenLocal]
    publishing {
        repositories {
            maven {
                name = "myRepoSnapshots"
                url = uri("https://my.repo/")
                // See Gradle docs for how to provide credentials to PasswordCredentials
                // https://docs.gradle.org/current/samples/sample_publishing_credentials.html
                credentials(PasswordCredentials::class)
            }
        }
    }
}

publishing {
    // Publishing dev bundle:
    // ./gradlew publishDevBundlePublicationTo(MavenLocal|MyRepoSnapshotsRepository) -PpublishDevBundle
    publications.create<MavenPublication>("devBundle") {
        artifact(tasks.generateDevelopmentBundle) {
            artifactId = "dev-bundle"
        }
    }
}
