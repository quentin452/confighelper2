

# confighelper
make modpacks with ease

This mod is a fork of https://github.com/jredfox/confighelper maked with https://github.com/anatawa12/ForgeGradle-example template

to trying to make compats between endlessids , biome ids extender and confighelper


# setup asm
for loading in deob `-Dfml.coreMods.load=jml.evilnotch.lib.asm.ASMPlugin`
for compiling input this into your build.gradle
make sure the build.gradle target compatability is JRE 1.8 as my interfaces require it

```
jar {
    manifest {
        attributes 'FMLCorePlugin': 'jml.evilnotch.lib.asm.ASMPlugin',
        'FMLCorePluginContainsFMLMod': 'false',
	    'FMLAT': 'confighelper_at.cfg'
    }
}
```

# compat between endlessids and confighelper(temporary)

**Endlessids crash when theses features are enabled in endlessids**
So disable pls in endlessids.cfg

```
extendPotion
extendBiome
extendEnchantment
```

# Biome ids Extender seem to be compatible

**no configurations needed**

# Whats the good way to extends all ids you wants(temporary)
| Name | Notes |
| --- | --- | 
|endlessids [<img src=https://media.forgecdn.net/avatars/130/458/636460205549127215.png height=16>](https://www.curseforge.com/minecraft/mc-mods/endlessids) [<img src=https://modrinth.com/favicon.ico height=16>](https://modrinth.com/mod/endlessids) [<img src=https://git-scm.com/favicon.ico height=16>](https://github.com/FalsePattern/EndlessIDs)| Extend block ids
|confighelper [<img src=https://git-scm.com/favicon.ico height=16>](https://github.com/quentin452/confighelperXendlessids)|Extend entity + DataWatcher + Potion + Enchant ids
|biome ids extender [<img src=https://media.forgecdn.net/avatars/130/458/636460205549127215.png height=16>](https://www.curseforge.com/minecraft/mc-mods/biome-id-extender) [<img src=https://modrinth.com/favicon.ico height=16>](https://modrinth.com/mod/biomeidextender) [<img src=https://git-scm.com/favicon.ico height=16>](https://github.com/TCLProject/Biome-ID-Extender)|Extend biome ids
