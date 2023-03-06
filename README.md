

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

# Compat between endlessids and confighelper

**Endlessids is compatible with confighelper if you disable theses features in endlessids.cfg(feature overlap)**

```
    extendDataWatcher
    extendEnchantment
    extendPotion
```

| Download Endlessids | Compat |
| --- | --- |
|[<img src=https://media.forgecdn.net/avatars/130/458/636460205549127215.png height=16>](https://www.curseforge.com/minecraft/mc-mods/endlessids) [<img src=https://modrinth.com/favicon.ico height=16>](https://modrinth.com/mod/endlessids) [<img src=https://git-scm.com/favicon.ico height=16>](https://github.com/FalsePattern/EndlessIDs)|Endlessids is compatible with confighelper if you disable theses features to feature overlap extendDataWatcher,extendEnchantment,extendPotion |
