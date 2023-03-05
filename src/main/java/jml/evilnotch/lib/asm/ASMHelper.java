package jml.evilnotch.lib.asm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import jml.evilnotch.lib.Validate;
import jml.evilnotch.lib.reflect.MCPSidedString;
import jml.evilnotch.lib.reflect.ReflectionHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

public class ASMHelper 
{	
	public static Map<String, ClassNode> classNodes = new HashMap();//input to classNode gets cleared when a MCWriter gets it's byte array
	
	/**
	 * doesn't patch local variable or method calls
	 */
	public static MethodNode replaceMethod(ClassNode node, String input, String name, String desc)
	{
		try
		{
			MethodNode origin = getMethod(node, name, desc);
			MethodNode toReplace = getMethod(input, name, desc);
			origin.instructions = toReplace.instructions;
			origin.localVariables = toReplace.localVariables;
			origin.annotationDefault = toReplace.annotationDefault;
			origin.tryCatchBlocks = toReplace.tryCatchBlocks;
			origin.visibleAnnotations = toReplace.visibleAnnotations;
			origin.visibleLocalVariableAnnotations = toReplace.visibleLocalVariableAnnotations;
			origin.visibleTypeAnnotations = toReplace.visibleTypeAnnotations;
			return origin;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ClassNode getClassNodeCached(String input) throws IOException 
	{
		if(classNodes.containsKey(input))
		{
			return classNodes.get(input);
		}
		ClassNode node = getClassNode(input);
		classNodes.put(input, node);
		return node;
	}
	
	/**
	 * after editing one class call this for cleanup
	 */
	public static void clear() 
	{
		classNodes.clear();
	}
	
	public static ClassNode getClassNode(String input) throws IOException 
	{
		InputStream stream = ASMHelper.class.getClassLoader().getResourceAsStream(input);
		return getClassNode(stream);
	}
	
	/**
	 * doesn't get cached for further uses
	 */
	public static ClassNode getClassNode(InputStream stream) throws IOException 
	{
		byte[] newbyte = IOUtils.toByteArray(stream);
		return getClassNode(newbyte);
	}
	
	/**
	 * if you already have the bytes and you don't need the class reader
	 */
	public static ClassNode getClassNode(byte[] newbyte)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(newbyte);
		classReader.accept(classNode, 0);
		return classNode;
	}
	
	/**
	 * if you use the ITransformer library call this method to replace a full class from another
	 */
	public static void replaceClassNode(ClassNode org, String input) throws IOException
	{
		replaceClassNode(org, getClassNode(input));
	}
	
	/**
	 * if you use the ITransformer library call this method to replace a full class from another
	 */
	public static void replaceClassNode(ClassNode org, ClassNode node) throws IOException
	{
		 org.access = node.access;
		 org.attrs = node.attrs;
		 org.fields = node.fields;
		 org.innerClasses = node.innerClasses;
		 org.interfaces = node.interfaces;
		 org.invisibleAnnotations = node.invisibleAnnotations;
		 org.invisibleTypeAnnotations = node.invisibleTypeAnnotations;
		 org.methods = node.methods;
		 org.name = node.name;
		 org.outerClass = node.outerClass;
		 org.outerMethod = node.outerMethod;
		 org.outerMethodDesc = node.outerMethodDesc;
		 org.signature = node.signature;
		 org.sourceDebug = node.sourceDebug;
		 org.sourceFile = node.sourceFile;
		 org.superName = node.superName;
		 org.version = node.version;
		 org.visibleAnnotations = node.visibleAnnotations;
		 org.visibleTypeAnnotations = node.visibleTypeAnnotations;
	}
	
	public static MCWriter getClassWriter(ClassNode node) 
	{
		MCWriter writer = new MCWriter();
		node.accept(writer);
		return writer;
	}
	
	/**
	 * if you don't need to compute frames or maxes as only strings have changed
	 */
	public static MCWriter getWriterForReob(ClassNode node)
	{
		MCWriter writer = new MCWriter(0);
		node.accept(writer);
		return writer;
	}
	
	public static void patchMethod(MethodNode method, String name, String oldName)
	{
		patchMethod(method, name, oldName, false);
	}
	
	/**
	 * patch a method you can call this directly after replacing it
	 */
	public static void patchMethod(MethodNode method, String name, String oldName, boolean patchStatic)
	{
		patchInsns(method, name, oldName, patchStatic);
		patchThis(method, name);
	}
	
	/**
	 * patch all references on the local variable table instanceof of this to a new class
	 */
	public static void patchThis(MethodNode method, String name)
	{
		LocalVariableNode local = ASMHelper.getLocalVarByName(method, "this");
		local.desc = toASMDesc(name);
	}

	/**
	 * patch previous object owner instructions to new owner with filtering out static fields/method calls
	 */
	public static void patchInsns(MethodNode method, String classNew, String classOld, boolean patchStatic) 
	{
		classNew = toASMClass(classNew);
		classOld = toASMClass(classOld);
		
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(ab instanceof MethodInsnNode)
			{
				MethodInsnNode min = (MethodInsnNode)ab;
				if(min.owner.equals(classOld) && (!isStaticMethod(min) || patchStatic) )
					min.owner = classNew;
			}
			else if(ab instanceof FieldInsnNode)
			{
				FieldInsnNode fin = (FieldInsnNode)ab;
				if(fin.owner.equals(classOld) && (!isStaticFeild(fin) || patchStatic) )
					fin.owner = classNew;
			}
		}
	}
	
	public static String toASMClass(String name)
	{
		return name.replace('.', '/');
	}
	
	
	public static String toASMDesc(String name) 
	{
		return "L" + toASMClass(name) + ";";
	}
	
	/**
	 * get a method node from a possble cached classnode
	 */
	public static MethodNode getMethod(String input, String name, String desc) throws IOException 
	{
		return getMethod(getClassNodeCached(input), name, desc);
	}
	
	public static MethodNode getMethod(ClassNode node, String name, String desc) 
	{
		for (MethodNode method : node.methods)
		{
			if (method.name.equals(name) && method.desc.equals(desc))
				return method;
		}
		return null;
	}
	
	/**
	 * get a constructor since they are MethodNodes
	 */
	public static MethodNode getConstructor(ClassNode node, String desc) 
	{
		return getMethod(node, "<init>", desc);
	}
	
	public static MethodNode getClassInit(ClassNode node) 
	{
		return getMethod(node, "<clinit>", "()V");
	}
	
	public static FieldNode getField(ClassNode node, String name)
	{
		for(FieldNode f : node.fields)
			if(f.name.equals(name))
				return f;
		return null;
	}
	
	
	/**
	 * get a local variable index by it's owner name
	 */
	public static int getLocalVarIndex(MethodNode method, String owner, String name) 
	{
		for(LocalVariableNode local : method.localVariables)
		{
			if(local.desc.equals(owner) && local.name.equals(name))
				return local.index;
		}
		return -1;
	}
	
	public static int getLocalVarIndexByName(MethodNode method, String name) 
	{
		for(LocalVariableNode local : method.localVariables)
		{
			if(local.name.equals(name))
				return local.index;
		}
		return -1;
	}
	
	public static LocalVariableNode getLocalVar(MethodNode method, String owner, String name)
	{
		return method.localVariables.get(getLocalVarIndex(method, owner, name));
	}
	
	public static LocalVariableNode getLocalVarByName(MethodNode method, String name)
	{
		return method.localVariables.get(getLocalVarIndexByName(method, name));
	}
	
	/**
	 * add an interface to a class
	 */
	public static void addInterface(ClassNode node, String name)
	{
		node.interfaces.add(name);
	}
	
	/**
	 * add a object field to the class
	 */
	public static void addFeild(ClassNode node, String name, String desc)
	{
		addFeild(node, name, desc, null);
	}
	
	/**
	 * add a object field to the class with optional signature. The paramDesc is a descriptor of the types of a class HashMap<key,value>
	 */
	public static void addFeild(ClassNode node, String feildName, String desc, String sig)
	{
		FieldNode field = new FieldNode(Opcodes.ACC_PUBLIC, feildName, desc, sig, null);
		node.fields.add(field);
	}
	
	/**
	 * don't add the method if it's already has it
	 */
	public static void addIfMethod(ClassNode node, String input, String name, String desc) throws IOException
	{
		MethodNode method = getMethod(input, name, desc);
		Validate.nonNull(method);
		addIfMethod(node, method);
	}
	
	/**
	 * add a brand new method node into the classNode
	 */
	public static void addIfMethod(ClassNode node, int access, String name, String desc) 
	{
		MethodNode method = new MethodNode(access, name, desc, null, null);
		addIfMethod(node, method);
	}

	public static void addIfMethod(ClassNode node, MethodNode method) 
	{
		if(!containsMethod(node, method))
			node.methods.add(method);
	}

	/**
	 * add a method no obfuscated checks you have to do that yourself if you got a deob compiled class
	 * no checks for patching the local variables nor the instructions
	 */
	public static MethodNode addMethod(ClassNode node, String input, String name, String desc) throws IOException 
	{
		MethodNode method = getMethod(input, name, desc);
		Validate.nonNull(method);
		node.methods.add(method);
		return method;
	}
	
	public static boolean containsMethod(ClassNode node, MethodNode method) 
	{
		return containsMethod(node, method.name, method.desc);
	}
	
	/**
	 * search from the class node if it contains the method
	 * @return
	 */
	public static boolean containsMethod(ClassNode node, String name, String desc) 
	{
		for(MethodNode m : node.methods)
			if(m.name.equals(name) && m.desc.equals(desc))
				return true;
		return false;
	}
	
	/**
	 * remove a method don't remove ones that are going to get executed unless you immediately add the same method and descriptor back
	 * @throws IOException 
	 */
	public static void removeMethod(ClassNode node, String name, String desc) throws IOException
	{
		MethodNode method = getMethod(node, name, desc);
		if(method != null)
			node.methods.remove(method);
	}
	
	public static void removeField(ClassNode node, String name) 
	{
		FieldNode f = getField(node, name);
		if(f != null)
			node.fields.remove(f);
	}
	
	/**
	 * find the first instruction to inject
	 */
	public static AbstractInsnNode firstInsn(MethodNode method, int opcode) 
	{
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(ab.getOpcode() == opcode)
			{
				return ab;
			}
		}
		return null;
	}
	
	/**
	 * getting the first instanceof of this will usually tell you where the initial injection point should be after
	 */
	public static LineNumberNode firstInsn(MethodNode method) 
	{
		for(AbstractInsnNode obj : method.instructions.toArray())
			if(obj instanceof LineNumberNode)
				return (LineNumberNode) obj;
		return null;
	}
	
	public static AbstractInsnNode lastInsn(MethodNode method)
	{
		AbstractInsnNode[] arr = method.instructions.toArray();
		for(int i=arr.length;i>=0;i--)
		{
			AbstractInsnNode ab = arr[i];
			if(!isReturnOpcode(ab.getOpcode()))
			{
				return ab;
			}
		}
		return null;
	}
	
	/**
	 * you can safely insert instructions in a constructor after the first super call
	 * not this won't check if the fields are instantiated before calling your bytecode if you need to use
	 * fields for your asm you will have to find a injection point further down the construction method
	 */
	public static AbstractInsnNode firstCtrInsn(MethodNode method)
	{
		return ASMHelper.firstInsn(method, Opcodes.INVOKESPECIAL);
	}
	
	/**
	 * helpful for finding injection point to the end of constructors
	 */
	public static AbstractInsnNode lastPutField(MethodNode method) 
	{
		return lastInsn(method, Opcodes.PUTFIELD);
	}
	
	/**
	 * optimized way of getting a last instruction
	 */
	public static AbstractInsnNode lastInsn(MethodNode method, int opCode) 
	{
		AbstractInsnNode[] arr = method.instructions.toArray();
		for(int i=arr.length-1;i>=0;i--)
		{
			AbstractInsnNode ab = arr[i];
			if(ab.getOpcode() == opCode)
				return ab;
		}
		return null;
	}

	public static MethodInsnNode lastMethodInsn(MethodNode method, MethodInsnNode compare) 
	{
		AbstractInsnNode[] list = method.instructions.toArray();
		for(int i = list.length-1; i >=0 ; i--)
		{
			AbstractInsnNode ab = list[i];
			if(equals(compare, ab) )
			{
				return (MethodInsnNode)ab;
			}
		}
		return null;
	}
	
	public static FieldInsnNode lastFieldInsn(MethodNode method, FieldInsnNode compare) 
	{
		AbstractInsnNode[] list = method.instructions.toArray();
		for(int i = list.length-1; i >=0 ; i--)
		{
			AbstractInsnNode ab = list[i];
			if(equals(compare, ab) )
			{
				return (FieldInsnNode)ab;
			}
		}
		return null;
	}
	
	public static FieldInsnNode firstFieldInsn(MethodNode method, FieldInsnNode field) 
	{
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(ASMHelper.equals(field, ab))
			{
				return (FieldInsnNode) ab;
			}
		}
		return null;
	}
	
	public static MethodInsnNode firstMethodInsn(MethodNode method, MethodInsnNode insn) 
	{
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(ASMHelper.equals(insn, ab))
			{
				return (MethodInsnNode) ab;
			}
		}
		return null;
	}
	
	public static LdcInsnNode firstLdcInsn(MethodNode method, LdcInsnNode ldc) 
	{
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(ASMHelper.equals(ldc, ab))
			{
				return (LdcInsnNode) ab;
			}
		}
		return null;
	}

	public static JumpInsnNode nextJumpInsn(AbstractInsnNode starting) 
	{
		AbstractInsnNode k = starting;
		while(k != null)
		{
			k = k.getNext();
			if(k instanceof JumpInsnNode)
				return (JumpInsnNode) k;
		}
		return null;
	}
	
	public static FieldInsnNode nextFieldInsn(AbstractInsnNode starting) 
	{
		AbstractInsnNode k = starting;
		while(k != null)
		{
			k = k.getNext();
			if(k instanceof FieldInsnNode)
				return (FieldInsnNode) k;
		}
		return null;
	}
	
	public static FieldInsnNode nextFieldInsn(AbstractInsnNode starting, FieldInsnNode compare) 
	{
		AbstractInsnNode k = starting;
		while(k != null)
		{
			k = k.getNext();
			if(k instanceof FieldInsnNode && equals(compare, k))
				return (FieldInsnNode) k;
		}
		return null;
	}
	
	public static MethodInsnNode nextMethodInsn(AbstractInsnNode starting, MethodInsnNode insn) 
	{
		AbstractInsnNode k = starting;
		while(k != null)
		{
			k = k.getNext();
			if(k instanceof MethodInsnNode && equals(insn, k))
				return (MethodInsnNode) k;
		}
		return null;
	}
	
	public static MethodInsnNode nextMethodInsn(AbstractInsnNode starting) 
	{
		AbstractInsnNode k = starting;
		while(k != null)
		{
			k = k.getNext();
			if(k instanceof MethodInsnNode)
				return (MethodInsnNode) k;
		}
		return null;
	}

	public static IntInsnNode nextIntInsn(AbstractInsnNode ab) 
	{
		while(ab != null)
		{
			ab = ab.getNext();
			if(ab instanceof IntInsnNode)
				return (IntInsnNode) ab;
		}
		return null;
	}

	public static AbstractInsnNode nextInsn(AbstractInsnNode ab, int opcode) 
	{
		while(ab != null)
		{
			ab = ab.getNext();
			if(ab != null && ab.getOpcode() == opcode)
				return ab;
		}
		return null;
	}
	
	public static AbstractInsnNode nextLineNumber(AbstractInsnNode ab) 
	{
		while(ab != null)
		{
			ab = ab.getNext();
			if(ab instanceof LineNumberNode)
				return ab;
		}
		return null;
	}
	
	public static AbstractInsnNode nextLabel(AbstractInsnNode ab) 
	{
		while(ab != null)
		{
			ab = ab.getNext();
			if(ab instanceof LabelNode)
				return ab;
		}
		return null;
	}
	
	public static AbstractInsnNode previousLabel(AbstractInsnNode ab) 
	{
		while(ab != null)
		{
			ab = ab.getPrevious();
			if(ab instanceof LabelNode)
				return ab;
		}
		return null;
	}
	
	/**
	 * grab a push code for a num value
	 */
	public static AbstractInsnNode getPushInsn(int value) throws IllegalArgumentException
	{
		if(value <= Byte.MAX_VALUE)
			return new IntInsnNode(Opcodes.BIPUSH, value);
		if(value <= Short.MAX_VALUE)
			return new IntInsnNode(Opcodes.SIPUSH, value);
		return new LdcInsnNode(value);
	}
	
	/**
	 * delete a whole line instructions sectioned between two line number nodes
	 * will not delete FrameNodes for binary compatibility for adding more lines later
	 * ONLY SUPPORTS NORMAL LINES no for loops, if statements etc...
	 */
	public static void removeLine(MethodNode method, AbstractInsnNode start)
	{
		if(!(start instanceof LineNumberNode))
			start = ASMHelper.previousLabel(start).getNext();
		if(start instanceof FrameNode)
			start = start.getNext();
		AbstractInsnNode end = ASMHelper.nextLabel(start).getPrevious();//previous is label and the one before the label is the end
		ASMHelper.removeInsn(method, start, end);
	}
	
	/**
	 * null checks
	 */
	public static void removeInsn(MethodNode method, AbstractInsnNode start) 
	{
		if(start != null)
			method.instructions.remove(start);
	}

	/**
	 * make sure the start and end are fetched directly from the method node
	 */
	public static void removeInsn(MethodNode method, AbstractInsnNode start, AbstractInsnNode end) 
	{
		while(start != end)
		{
			AbstractInsnNode next = start.getNext();
			method.instructions.remove(start);
			start = next;
		}
		method.instructions.remove(end);
	}
	
	/**
	 * works if no other transformers editing bytecode so really not recommended to use. 
	 * only use for debugging or deob with no other core mods enabled
	 */
	@Deprecated
	public static void removeInsn(MethodNode method, AbstractInsnNode start, int size) 
	{
		for(int i=0; i < size; i++)
		{
			AbstractInsnNode next = start.getNext();
			method.instructions.remove(start);
			start = next;
		}
	}
	
	public static void clearVoidMethod(MethodNode method) 
	{
		clearMethod(method);
		method.instructions.add(new InsnNode(Opcodes.RETURN));
	}

	/**
	 * warning if you clear a method you must input the return type manually
	 */
	public static void clearMethod(MethodNode method) 
	{
		LineNumberNode line = ASMHelper.firstInsn(method);
		method.instructions.clear();
		LabelNode label = new LabelNode();
		method.instructions.add(label);
		method.instructions.add(new LineNumberNode(line.line, label));
	}

	public static void clearNextThrowable(MethodNode method, String desc_exception) 
	{
		AbstractInsnNode start = null;
		AbstractInsnNode end = null;
		for(AbstractInsnNode ab : method.instructions.toArray())
		{
			if(Opcodes.NEW == ab.getOpcode())
			{
				TypeInsnNode type = (TypeInsnNode)ab;
				if(type.desc.equals(desc_exception))
				{
					start = ab;
					end = ASMHelper.nextInsn(start, Opcodes.ATHROW);
					break;
				}
			}
		}
		if(start != null)
			ASMHelper.removeInsn(method, start, end);
	}
	
	private static Field opField = ReflectionHandler.getField(AbstractInsnNode.class, "opcode");
	/**
	 * use this if you can't find a setter for something extending AbstractInsnNode
	 */
	public static void setOpcode(AbstractInsnNode ab, int opcode)
	{
		ReflectionHandler.set(opField, ab, opcode);
	}
	
	public static boolean equals(MethodInsnNode m1, AbstractInsnNode ab)
	{
		if(!(ab instanceof MethodInsnNode))
			return false;
		MethodInsnNode m2 = (MethodInsnNode)ab;
		return m1.getOpcode() == m2.getOpcode() && m1.name.equals(m2.name) && m1.desc.equals(m2.desc) && m1.owner.equals(m2.owner) && m1.itf == m2.itf;
	}
	
	public static boolean equals(FieldInsnNode f1, AbstractInsnNode ab)
	{
		if(!(ab instanceof FieldInsnNode))
			return false;
		FieldInsnNode f2 = (FieldInsnNode)ab;
		return f1.getOpcode() == f2.getOpcode() && f1.name.equals(f2.name) && f1.desc.equals(f2.desc) && f1.owner.equals(f2.owner);
	}
	
	public static boolean equals(LdcInsnNode l1, AbstractInsnNode ab) 
	{
		if(!(ab instanceof LdcInsnNode))
			return false;
		LdcInsnNode l2 = (LdcInsnNode)ab;
		return l1.cst.equals(l2.cst);
	}
	
	public static boolean equals(FieldNode f1, FieldNode f2)
	{
		return f1.name.equals(f2.name) && f1.desc.equals(f2.desc);
	}
	
	public static boolean equals(MethodNode m1, MethodNode m2)
	{
		return m1.name.equals(m2.name) && m1.desc.equals(m2.desc);
	}
	
	public static String toString(FieldNode field) 
	{
		return field.name + " desc:" + field.desc + " signature:" + field.signature + " access:" + field.access;
 	}
	
	public static String toString(MethodNode method) 
	{
		return method.name + " desc:" + method.desc + " signature:" + method.signature + " access:" + method.access;
 	}
	
	/**
	 * get the standard recommended input stream for asm
	 */
	public static String getInputStream(String modid, String simpleName) 
	{
		return "assets/" + modid + "/asm/" + (ObfHelper.isObf ? "srg/" : "deob/") + simpleName;
	}
	
	/**
	 * get a classes simple name without loading it
	 */
	public static String getSimpleName(String clazz)
	{
		String[] args = clazz.split("\\.");
		return args[args.length-1];
	}
	
	/**
	 * this will determine if the node is static or not
	 */
	public static boolean isStaticMethod(MethodInsnNode method) 
	{
		int opcode = method.getOpcode();
		return Opcodes.INVOKESTATIC == opcode;
	}
	
	/**
	 *this will determine if the node is static or not
	 */
	public static boolean isStaticFeild(FieldInsnNode field) 
	{
		int opcode = field.getOpcode();
		return Opcodes.GETSTATIC == opcode || Opcodes.PUTSTATIC == opcode;
	}

	public static boolean isReturnOpcode(int opcode)
	{
		return opcode == Opcodes.RETURN || opcode == Opcodes.ARETURN || opcode == Opcodes.DRETURN || opcode == Opcodes.FRETURN || opcode == Opcodes.IRETURN || opcode == Opcodes.LRETURN;
	}
	
	/**
	 * use this in your asm to return true
	 */
	public static boolean isTrue()
	{
		return true;
	}
	
	/**
	 * use this is your asm to return false
	 */
	public static boolean isFalse()
	{
		return false;
	}
	
	
	/**
	 * dumps a file from memory
	 */
	public static void dumpFile(String name, ClassWriter writer) throws IOException 
	{
		dumpFile(name, writer.toByteArray());
	}
	
	/**
	 * dumps a file from memory
	 */
	public static void dumpFile(String name, byte[] bytes) throws IOException 
	{
    	name = name.replace('.', '/');
    	File f = new File(getHome(), "asm/dumps/" + name + ".class");
    	f.getParentFile().mkdirs();
    	FileUtils.writeByteArrayToFile(f, bytes);
	}
	
	/**
	 * Launch.minecraftHome is null in deob and is valid in ob
	 */
	public static File getHome()
	{
		return Launch.minecraftHome != null ? Launch.minecraftHome : new File(System.getProperty("user.dir"));
	}
	
	public static File getConfig()
	{
		return new File(ASMHelper.getHome(), "config");
	}

	public static String getMethodDescriptor(Class clazz, String name, Class... params)
	{
		Method m = ReflectionHandler.getMethod(clazz, name, params);
		if(m == null)
			return null;
	    String s = "(";
	    for(final Class c : m.getParameterTypes())
	    {
	        s += getTypeForClass(c);
	    }
	    s+=')';
	    return s + getTypeForClass(m.getReturnType());
	}
	
	public static String getTypeForClass(Class c)
	{
	    if(c.isPrimitive())
	    {
	        if(c==byte.class)
	            return "B";
	        if(c==char.class)
	            return "C";
	        if(c==double.class)
	            return "D";
	        if(c==float.class)
	            return "F";
	        if(c==int.class)
	            return "I";
	        if(c==long.class)
	            return "J";
	        if(c==short.class)
	            return "S";
	        if(c==boolean.class)
	            return "Z";
	        if(c==void.class)
	            return "V";
	        throw new RuntimeException("Unrecognized primitive " + c);
	    }
	    else if(c.isArray()) 
	    {
	    	return ASMHelper.toASMClass(c.getName()) + ";";
	    }
	    return ASMHelper.toASMDesc(c.getName());
	}
}
