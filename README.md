<img src="toki_logo.png" align="right" width="150" height="150"/>

# Toki

Toki is a server-side modding platform which makes it possible to create and port [Fabric](https://github.com/FabricMC/fabric) mods on the [Paper](https://github.com/PaperMC/Paper) server software and its forks.

See the [Toki example mod](https://github.com/TetraTau/toki-example-mod/tree/master) as a reference.

### !WARNING!
1. The project doesn't let you run already existing [Fabric](https://github.com/FabricMC/fabric) mods, instead it aims to provide developers an easy tool for porting those mods for [Paper](https://github.com/PaperMC/Paper) and its forks.
2. The project is highly WIP, expect bugs and limitation in the toolchain, please create issues for bug reports and feature requests.

### Join [discord](https://discord.gg/upTtNyvkNf) for discussion

## Download
For now you can download builds only from our [Github releases](https://github.com/TetraTau/Toki/releases)

## Infrastructure
### [Tokimak](https://github.com/TetraTau/tokimak)
This is a Gradle plugin which provides Paper source code and Loom's features like Mixin remapping.
For now it is WIP, but it is suitable for basic Mixin development using Mojang mappings.

### [Toki installer](https://github.com/TetraTau/toki-installer)
This is an application and library which lets you install Toki on forks of Paper. \
It is also used in the Toki's buildscript for building purposes.

## Motivation and goals
This project exists to solve two problems: *Hybrid server software problem* and *Plugin API limitation problem*.

### Hybrid server software problem
Server admins often want to have modded servers which 1) support bukkit plugins 2) have good performance.

The issue with hybrid server software is stability and support, almost none of mods or plugins provide an official support for hybrid server software, so for now the sanest solution is to use the vanilla server software with mods which 1) replace plugins 2) optimize the server.

The main issue there is optimization mods, which usually don't do invasive changes for the sake of performance. 
On the other hand, Paper does such changes using its patch system and performs better than almost all of the server optimization modpacks,
so the solution this project provides is providing an easy to use toolchain and APIs for making / porting fabric mods to it.

### Plugin API limitiation problem
Usually plugins can't modify Minecraft server internals, and if they do, they do it badly.
Plugins don't have an official support of modding tools like Mixins and Access wideners,
on one hand it makes plugins more compatible with each other, on the other hand it limits plugin possibilites.

So if you're a plugin developer willing to do more than usual plugins are capable of, you can use this platform, make mods and run them on Paper or any other Paper fork.

## TODOs / Plans
- [x] Implement Fabric Loader on top of Paper.
- [x] Create a Gradle plugin for developers to be able to develop mods with this project.
- [ ] Create a Toki API mod which has a compatibility with the Fabric API on the client side. (Registry sync)
- [ ] Make Bukkit API flexible enough to handle modded content.

## Contributing and Technical documentation
See [this file](https://github.com/TetraTau/Toki/blob/master/TECH_DOCS_AND_CONTRIBUTING.md).
