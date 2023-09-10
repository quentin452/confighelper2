package jml.confighelper.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import jml.evilnotch.lib.asm.Coremod;
import jml.evilnotch.lib.asm.TransformsReg;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Plugin implements Coremod {

    @Override
    public void registerTransformers() {
        TransformsReg.registerTransformer("jml.confighelper.asm.RegTransformer");
        TransformsReg.registerTransformer("jml.confighelper.asm.RegCompatTransformer");
        TransformsReg.registerTransformer("jml.ids.asm.IdsTransformer");
    }

}
