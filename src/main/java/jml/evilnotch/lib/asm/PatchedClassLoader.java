package jml.evilnotch.lib.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import jml.evilnotch.lib.minecraft.CrashReport;
import jml.evilnotch.lib.reflect.ReflectionHandler;
import jml.evilnotch.lib.simple.DummyMap;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.LogWrapper;
import scala.reflect.internal.Trees.This;

public class PatchedClassLoader {

	public static final Field cachedClasses = ReflectionHandler.getField(LaunchClassLoader.class, "cachedClasses");
	public static final Field resourceCache = ReflectionHandler.getField(LaunchClassLoader.class, "resourceCache");
	public static final Field packageManifets = ReflectionHandler.getField(LaunchClassLoader.class, "packageManifests");
	
	/**
	 * stop memory overflow
	 */
	public static void stopMemoryOverflow(LaunchClassLoader loader)
	{
		ReflectionHandler.set(cachedClasses, loader, new DummyMap());
		ReflectionHandler.set(resourceCache, loader, new DummyMap());
		ReflectionHandler.set(packageManifets, loader, new DummyMap());
	}
	
	public static boolean isOptimized(ClassLoader loader)
	{
		if(!(loader instanceof LaunchClassLoader))
		{
			return false;
		}
		return ReflectionHandler.get(cachedClasses, loader) instanceof DummyMap &&
			   ReflectionHandler.get(resourceCache, loader) instanceof DummyMap &&
			   ReflectionHandler.get(packageManifets, loader) instanceof DummyMap;
	}

	public static void checkClassLoader(ClassLoader loader) 
	{
    	boolean modded = Launch.classLoader != loader;
    	if(modded)
    	{
    		System.out.println("modded ClassLoader Detected!" + loader.getClass().getName() + "," + Launch.classLoader.getClass().getName());
    	}
    	if(!PatchedClassLoader.isOptimized(loader) || modded && !PatchedClassLoader.isOptimized(Launch.classLoader))
    	{
    		CrashReport.makeCrashReport("init", "LaunchClassLoader is unoptimized!");
    	}
	}
	
	/**
	 * in versions 1.12.2+
	 */
	public static void foamFixSupport()
	{
		
	}

}
