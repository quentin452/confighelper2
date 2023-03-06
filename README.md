# Using anatawa fork to change the source code and compile the mod https://github.com/anatawa12/ForgeGradle-example


Todo : remove biomes ids features etc to make it compatible with endlessids + biome ids extender

# This mod is a fork of https://github.com/jredfox/confighelper

# confighelper
make modpacks with ease

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

**Install endlessids + confighelper + biome ids extender