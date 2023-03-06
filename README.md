# Using anatawa fork to change the source code and compile the mod https://github.com/anatawa12/ForgeGradle-example


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


