package jml.evilnotch.lib.mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import jml.evilnotch.lib.asm.PatchedClassLoader;

@Mod(modid = LibReference.MODID, version = LibReference.VERSION, name = LibReference.NAME)
public class EvilNotchLib {

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        PatchedClassLoader.checkClassLoader(
            this.getClass()
                .getClassLoader());
    }
}
