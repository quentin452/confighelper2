package jml.evilnotch.lib.asm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import jml.evilnotch.lib.reflect.ReflectionHandler;

public class TransformsReg {

    public static List<ITransformer> transformers = new ArrayList(2);
    public static Set<ResourceLocation> tcheck = new HashSet(2);
    public static Set<String> exclusions = new HashSet(0);

    public static void registerTransformer(String transformerClass) {
        try {
            ITransformer transformer = (ITransformer) Launch.classLoader.loadClass(transformerClass)
                .newInstance();
            ResourceLocation id = transformer.getId();
            if (tcheck.contains(id)) {
                throw new IllegalArgumentException("DUPLICATE ASM ITransformer\t:" + transformer.getId());
            }
            transformers.add(transformer);
            tcheck.add(id);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void registerCoreMods() {
        List<Object> list = (List<Object>) ReflectionHandler
            .get(ReflectionHandler.getField(CoreModManager.class, "loadPlugins"), null);
        Class wrapperClass = ReflectionHandler.getClass("cpw.mods.fml.relauncher.CoreModManager$FMLPluginWrapper");
        Field cmodGetter = ReflectionHandler.getField(wrapperClass, "coreModInstance");
        for (Object wrapper : list) {
            IFMLLoadingPlugin plugin = (IFMLLoadingPlugin) ReflectionHandler.get(cmodGetter, wrapper);
            if (plugin instanceof Coremod) {
                System.out.println(
                    "Coremod found:" + plugin.getClass()
                        .getName());
                ((Coremod) plugin).registerTransformers();
            }
        }
        TransformsReg.sort();// sort the transformers after registering them
    }

    protected static void sort() {
        Collections.sort(transformers, new Comparator<ITransformer>() {

            @Override
            public int compare(ITransformer o1, ITransformer o2) {
                return ((Integer) o1.sortingIndex()).compareTo(o2.sortingIndex());
            }
        });
    }

    public static void registerExclusion(String startingName) {
        exclusions.add(startingName);
    }

    public static void unregisterExclusion(String startingName) {
        exclusions.remove(startingName);
    }

    public static boolean isExcluded(String name) {
        for (String s : exclusions) {
            if (name.startsWith(s)) return true;
        }
        return false;
    }

    public static String printIds() {
        StringBuilder b = new StringBuilder();
        String space = "\n";
        for (ITransformer t : transformers)
            b.append(space + "ITransformer:(" + t.getId() + ", class:" + t.getClass() + ")");
        return b.toString();
    }

    public static String printClasses() {
        StringBuilder b = new StringBuilder();
        String space = "\n";
        for (ITransformer t : transformers) b.append(space + "ITransformer:(" + t.getClass() + ")");
        return b.toString();
    }

    public static List<ResourceLocation> getIds() {
        List<ResourceLocation> li = new ArrayList();
        for (ITransformer t : transformers) li.add(t.getId());
        return li;
    }

}
