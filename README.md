<img src="toki_logo.png" align="right" width="150" height="150"/>

# Toki

Toki is a Paper fork which has built in Fabric loader.

### Join [discord](https://discord.gg/upTtNyvkNf) for discussion

## State
Highly WIP. For now it launches without errors, but there is no remapping from spigot to intermediary/obfuscated, 
it means that none of exisitng mods work on it, implementation / fixes soon!

## Reasons, purpose and policy
The reasons why it exists are:
1. To allow easy mixin / mod development on CraftBukkit based platforms for developers who want to do it.
2. (Higly experimental, generally not supported) To allow some already existing fabric mods to run on a CraftBukkit based platform.

## TODOs / Plans
- [ ] Remapping of patched Paper server to whatever fabric uses at runtime to theoretically allow *some* mods to work.
- [ ] Gradle plugin to allow mod development with Toki.
- [ ] Include Paper Plugins PR and allow plugins depend on fabric mods using `paper-plugin.yml`.
- [ ] Make Bukkit API flexible enough to handle modded content. (Using Converters / EnumCreators from Registry PR)
