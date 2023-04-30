<img src="toki_logo.png" align="right" width="150" height="150"/>

# Toki

Toki is a Paper fork which provides a Fabric-based modding platform for developers.

### Join [discord](https://discord.gg/upTtNyvkNf) for discussion

## State
Everything is still WIP, but all Fabric loader functionality works. There is a [Gradle plugin](https://github.com/TetraTau/tokimak) for developers to develop mods for this platform. \
If you're a developer and interested in the mixin usage, see the [Toki example mod](https://github.com/TetraTau/toki-example-mod/tree/master).
See TODOs / Plans for more info about the state.

## Download
For now you can download builds only from our [Github releases](https://github.com/TetraTau/Toki/releases)

## Q & A
Q: Will my fabric mod `X` work on it? \
A: No, as far as mod developer didn't port their mod on this platform.

Q: Why it doesn't allow to launch already existing Fabric mods? \
A: It is so unlikely that already exisiting mods will work, Paper makes so many changes so they break mods. (Though, if you are a developer and you have knowledge of mapping generation, you can discuss adding remapping of mods from intermediary to spigot as an experimental feature for this project.)

## How to build
Generally, the build process the same as Paper's, except you also need to run `./gradlew applyFabricLoaderPatches` and `./gradlew applyPaperclipPatches`.
```
git clone https://github.com/TetraTau/Toki.git
./gradlew applyPatches
./gradlew applyFabricLoaderPatches
./gradlew applyPaperclipPatches
  For producion: (spigot mappings)
./gradlew createReobfPaperclipJar
  For testing: (mojang mappings)
./gradlew createMojmapBundlerJar
```
## TODOs / Plans
- [x] Implement Fabric Loader on top of Paper.
- [x] Create a Gradle plugin for developers to be able to develop mods with this project.
- [ ] Create a Toki API which has a compatibility with the Fabric API on the client side. (Registry sync)
- [ ] Make Bukkit API flexible enough to handle modded content.

## How it works?
This project patches Paperclip to support Fabric Loader. Patched Paperclip contains patched Fabric Loader jar, then it extracts it and installs needed libraries, then it makes patched Paper server jar and launches patched Fabric Loader telling it to start the patched server Jar. Patched Fabric Loader has changes in Knot classpath libraries, because Paper ships additional libraries and we need to tell Fabric Loader to load them, and also it has changes in the entrypoint patch, because CraftBukkit drastically changes `net.minecraft.server.Main`.
