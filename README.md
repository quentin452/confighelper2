

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

**Endlessids is incompatible**


# Compat between biome ids extender and confighelper

**seem compatible , no configuration needed**

# Whats the good way to extends all ids you wants

for now no good way

# note

Biome ids Extender is incompatible with endlessids