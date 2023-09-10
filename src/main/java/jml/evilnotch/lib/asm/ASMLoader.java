package jml.evilnotch.lib.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.tree.ClassNode;

public class ASMLoader implements IClassTransformer {

    private static boolean reg;

    public ASMLoader() {
        if (!reg) {
            TransformsReg.registerCoreMods();
            reg = true;
        }
    }

    @Override
    public byte[] transform(String oldName, String name, byte[] bytes) {
        if (bytes == null || TransformsReg.isExcluded(name)) return bytes;// do not parse null classes or blacklisted
                                                                          // classes

        ClassNode node = null;
        ITransformer last = null;
        try {
            for (ITransformer transformer : TransformsReg.transformers) {
                if (!transformer.canTransform(name)) continue;
                if (node == null) node = ASMHelper.getClassNode(bytes);
                last = transformer;
                transformer.transform(name, node);
            }
            if (last == null) return bytes;

            byte[] custom = ASMHelper.getClassWriter(node)
                .toByteArray();
            if (ObfHelper.isDeob) ASMHelper.dumpFile(name, custom);
            return custom;
        } catch (Throwable t) {
            t.printStackTrace();
            if (!name.equals("net.minecraft.util.ResourceLocation")) System.out.print(
                "Blamed Transformer:\t" + (last != null ? last.getId() : null)
                    + "\nLoaded Transformers:"
                    + TransformsReg.printIds()
                    + "\n");
            else System.out.print(
                "Blamed Transformer:\t" + (last != null ? last.getClass() : null)
                    + TransformsReg.printClasses()
                    + "\n");
        }
        return bytes;
    }

}
