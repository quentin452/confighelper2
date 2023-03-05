package jml.confighelper.asm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import jml.confighelper.reg.RegistryIds;
import jml.evilnotch.lib.JavaUtil;
import jml.evilnotch.lib.asm.ASMHelper;
import jml.evilnotch.lib.asm.ITransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.ResourceLocation;

public class RegCompatTransformer implements ITransformer{
	
	public static List<String> clazzes = JavaUtil.toArray(
	new String[]
	{
		"com.shinoow.abyssalcraft.AbyssalCraft",
		"erebus.ModBiomes"
	});
	
	@Override
	public ResourceLocation getId() 
	{
		return new ResourceLocation("confighelper:compat");
	}

	@Override
	public List<String> getClasses() 
	{
		return clazzes;
	}

	@Override
	public void transform(String name, ClassNode node) 
	{
		int index = clazzes.indexOf(name);
		if(index != -1)
		{
			System.out.println("config helper compat patching:" + node.name);
			switch(index)
			{
				case 0:
					patchAbyssalcraft(node);
				break;
				
				case 1:
					patchErebus(node);
				break;
			}
		}
	}
	
	private void patchErebus(ClassNode classNode) 
	{
		MethodNode init = ASMHelper.getMethod(classNode, "init", "()V");
		ASMHelper.clearNextThrowable(init, "java/lang/IllegalArgumentException");
		ASMHelper.clearNextThrowable(init, "java/lang/IllegalArgumentException");
		
		MethodNode post = ASMHelper.getMethod(classNode, "postInit", "()V");
		ASMHelper.clearVoidMethod(post);
	}

	private static void patchAbyssalcraft(ClassNode classNode) 
	{
		MethodNode method = ASMHelper.getMethod(classNode, "checkBiomeIds", "(Z)V");
		ASMHelper.clearVoidMethod(method);
	}

}
