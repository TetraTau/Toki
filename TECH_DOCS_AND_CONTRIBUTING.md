# Technical documentation and Contributing guide
## How to setup dev environment and build
First, you need to have Git and JDK installed. Use `git clone` command to clone the repository, **not** using the zip archive. \
Second, run those commands:
1. `./gradlew applyPaperclipPatches`
2. `./gradlew applyFabricLoaderPatches`
(These are gradle tasks) \
Now you have the patched Paperclip and Fabric Loader code, and you can edit it. \
If you want to build the toki, use:
* `./gradlew createPaprclipJar`
The jar will be in `build/libs`

## Understanding the patch system
This project uses a patch system identical to the one Paper uses, to understand how to create & modify patches see [this guide by Paper.](https://github.com/PaperMC/Paper/blob/master/CONTRIBUTING.md) \
One thing to note, instead of `Paper-API` and `Paper-Server` we have `fabric-loader` and `paperclip` subprojects.

## The project structure
### Fabric Loader
Toki forks and patches fabric loader to support the provided Paper server jar, for now it has changes in the Entrypoint ASM patch, and in a few other places.

### [Paperclip](https://github.com/PaperMC/Paperclip)
This is a bootstrap and distribution application written by PaperMC. This is the jar which both Paper and Toki release for people to use. \
The key difference with the usual Paperclip is that Toki's Paperclip supports Fabric Loader. \ 
In fact, Fabric Loader is built inside Paperclip Jar as a Jar file and when you launch Toki's Paperclip it downloads necessary libraries, unzips and loads the mod loader.

## [Tokimak Core](https://github.com/TetraTau/tokimak/tree/master/tokimak-core)
Tokimak core is a Gradle plugin which makes patch system and the automatic building proccess possible. \
While building, Tokimak Core downloads a Paper build defined in the `gradle.properties`, 
transfers the patch info to a Toki's paperclip Jar file, and it makes it run Paper.
