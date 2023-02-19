<img src="toki_logo.png" align="right" width="150" height="150"/>

# Toki

Toki is a Paper fork which provides a Fabric-based modding platform for developers.

### Join [discord](https://discord.gg/upTtNyvkNf) for discussion

## State
Highly WIP. The Fabric Loader on top of Paper is implemented, see `toki-test-mod`.
See TODOs / Plans for more info about the state.

## Q & A
Q: Will my fabric mod `X` work on it?
A: No, as far as mod developer didn't port their mod on this platform.

Q: Why it doesn't allow to launch already existing Fabric mods?
A: It is so unlikely that already exisiting mods will work, Paper makes so many changes so they break mods. (Though, if you are a developer and you have knowledge of mapping generation, you can discuss adding remapping of mods from intermediary to spigot as an experimental feature in this project.)

## TODOs / Plans
- [x] Implement Fabric Loader on top of Paper.
- [ ] Create a Loom fork which supports patched Paper server source.
- [ ] Create a Toki API which has a compatibility with the Fabric API on a client side. (Registry sync)
- [ ] Make Bukkit API flexible enough to handle modded content. (Using Converters / EnumCreators from the Paper Registry PR)

## How it works?
This project patches Paperclip to support Fabric Loader. Patched Paperclip contains patched Fabric Loader jar, then it extracts it and installs needed libraries, then it makes patched Paper server jar and launches patched Fabric Loader telling it to start the patched server Jar. Patched Fabric Loader has changes in Knot classpath libraries, because Paper ships additional libraries and we need to tell Fabric Loader to load them, and also it has changes in entrypoint, because CraftBukkit drastically changes `net.minecraft.server.Main`.
