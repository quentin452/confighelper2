package jml.evilnotch.lib.asm;

import net.minecraft.launchwrapper.Launch;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("ASMPlugin")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions("jml.evilnotch.lib.asm.")
public class ASMPlugin implements Coremod {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "jml.evilnotch.lib.asm.ASMLoader" };
    }

    @Override
    public void registerTransformers() {
        PatchedClassLoader.stopMemoryOverflow(Launch.classLoader);
    }

}
