package jml.evilnotch.lib.asm;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import net.minecraft.util.ResourceLocation;

public interface ITransformer {
	
	/**
	 * the transformer name essentially but, in resource location
	 */
	public ResourceLocation getId();
	/**
	 * do not return a new list every time used a cached one
	 * @return a list of classes in which your transformer will transform
	 */
	public List<String> getClasses();
	/**
	 * the transformer for your asm
	 */
	public void transform(String name, ClassNode node) throws Throwable;
	
	/**
	 * if your transformer is dynamic just always return true
	 */
	public default boolean canTransform(String name)
	{
		return this.getClasses().contains(name);
	}
	
	/**
	 * the order of preference you want to be sorted in don't worry srg names always occur ;)
	 * return 0 for you don't care, return -1 for first and Integer.MAX_VALUE for loading last
	 */
	public default int sortingIndex()
	{
		return 0;
	}

}
