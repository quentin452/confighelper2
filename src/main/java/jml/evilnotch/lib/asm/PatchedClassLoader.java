package jml.evilnotch.lib.asm;

import java.lang.reflect.Field;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import jml.evilnotch.lib.minecraft.CrashReport;
import jml.evilnotch.lib.reflect.ReflectionHandler;
import jml.evilnotch.lib.simple.DummyMap;

public class PatchedClassLoader {

    public static final Field cachedClasses = ReflectionHandler.getField(LaunchClassLoader.class, "cachedClasses");
    public static final Field resourceCache = ReflectionHandler.getField(LaunchClassLoader.class, "resourceCache");
    public static final Field packageManifets = ReflectionHandler.getField(LaunchClassLoader.class, "packageManifests");

    /**
     * stop memory overflow
     */
    public static void stopMemoryOverflow(LaunchClassLoader loader) {
        ReflectionHandler.set(cachedClasses, loader, new DummyMap());
        ReflectionHandler.set(resourceCache, loader, new DummyMap());
        ReflectionHandler.set(packageManifets, loader, new DummyMap());
    }

    public static boolean isOptimized(ClassLoader loader) {
        if (!(loader instanceof LaunchClassLoader)) {
            return false;
        }
        return ReflectionHandler.get(cachedClasses, loader) instanceof DummyMap
            && ReflectionHandler.get(resourceCache, loader) instanceof DummyMap
            && ReflectionHandler.get(packageManifets, loader) instanceof DummyMap;
    }

    public static void checkClassLoader(ClassLoader loader) {
        boolean modded = Launch.classLoader != loader;
        if (modded) {
            System.out.println(
                "modded ClassLoader Detected!" + loader.getClass()
                    .getName()
                    + ","
                    + Launch.classLoader.getClass()
                        .getName());
        }
        if (!PatchedClassLoader.isOptimized(loader) || modded && !PatchedClassLoader.isOptimized(Launch.classLoader)) {
            CrashReport.makeCrashReport("init", "LaunchClassLoader is unoptimized!");
        }
    }
}
