package jml.ids.asm;

import java.io.IOException;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import jml.confighelper.ModReference;
import jml.confighelper.reg.RegistryIds;
import jml.evilnotch.lib.asm.ASMHelper;
import jml.evilnotch.lib.asm.ITransformer;
import jml.evilnotch.lib.reflect.MCPSidedString;
import net.minecraft.util.ResourceLocation;

public class IdsTransformer implements ITransformer{
	
	public static final List<String> clazzes = RegistryIds.asList(new String[]
	{
		"net.minecraft.world.biome.BiomeGenBase",
		"net.minecraft.potion.Potion",
		"net.minecraft.enchantment.Enchantment",
		"net.minecraft.entity.EntityList",
		"net.minecraft.entity.DataWatcher",
		"net.minecraft.client.network.NetHandlerPlayClient",
		"net.minecraft.network.play.server.S1DPacketEntityEffect",
		"net.minecraft.network.play.server.S1EPacketRemoveEntityEffect",
		"net.minecraft.potion.PotionEffect",
		"net.minecraft.network.play.server.S0FPacketSpawnMob",
		"net.minecraft.item.ItemStack"
	});

	@Override
	public ResourceLocation getId() 
	{
		return new ResourceLocation("shortids:transformer");
	}

	@Override
	public List<String> getClasses() 
	{
		return clazzes;
	}

	@Override
	public void transform(String name, ClassNode node) throws IOException
	{
		int index = clazzes.indexOf(name);
		if(index != -1)
		{
			switch(index)
			{
				case 0:
					patchBiome(node);
				break;
				
				case 1:
					patchPotion(node);
				break;
				
				case 2:
					patchEnchantment(node);
				break;
				
				case 3:
					patchEntityList(node);
				break;
				
				case 4:
					patchDatawatcher(node);
				break;
				
				case 5:
					patchNetHandlerPlayClient(node);
				break;
				
				case 6:
					patchS1DPacketEntityEffect(node);
				break;
				
				case 7:
					patchS1EPacketRemoveEntityEffect(node);
				break;
				
				case 8:
					patchPotionEffect(node);
				break;
				
				case 9:
					patchS0FPacketSpawnMob(node);
				break;
				
				case 10:
					patchItemStack(node);
				break;
			}
		}
	}
	
	private void patchItemStack(ClassNode node) 
	{
		//patch write
		String setShort = new MCPSidedString("setShort", "func_74777_a").toString();
		String setInt = new MCPSidedString("setInteger", "func_74768_a").toString();
		String setByte = new MCPSidedString("setByte", "func_74774_a").toString();
		String descInt = "(Ljava/lang/String;I)V";
		MethodNode write = ASMHelper.getMethod(node, new MCPSidedString("writeToNBT", "func_77955_b").toString(), "(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;");
		MethodInsnNode m1 = ASMHelper.firstMethodInsn(write, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", setShort, "(Ljava/lang/String;S)V", false));
		MethodInsnNode m2 = ASMHelper.nextMethodInsn(m1, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", setByte, "(Ljava/lang/String;B)V", false));
		MethodInsnNode m3 = ASMHelper.nextMethodInsn(m2, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", setShort, "(Ljava/lang/String;S)V", false));
		m1.name = setInt;
		m1.desc = descInt;
		m2.name = setInt;
		m2.desc = descInt;
		m3.name = setInt;
		m3.desc = descInt;
		//remove casts
		write.instructions.remove(m1.getPrevious());
		write.instructions.remove(m2.getPrevious());
		write.instructions.remove(m3.getPrevious());
		
		//patch read getShort, getByte, getShort > getInteger, getInteger, getInteger
		String getShort = new MCPSidedString("getShort", "func_74765_d").toString();
		String getByte = new MCPSidedString("getByte", "func_74771_c").toString();
		String getInt = new MCPSidedString("getInteger", "func_74762_e").toString();
		String getIntDesc = "(Ljava/lang/String;)I";
		MethodNode read = ASMHelper.getMethod(node, new MCPSidedString("readFromNBT", "func_77963_c").toString(), "(Lnet/minecraft/nbt/NBTTagCompound;)V");
		MethodInsnNode m4 = ASMHelper.firstMethodInsn(read, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", getShort, "(Ljava/lang/String;)S", false));
		MethodInsnNode m5 = ASMHelper.nextMethodInsn(m4, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", getByte, "(Ljava/lang/String;)B", false));
		MethodInsnNode m6 = ASMHelper.nextMethodInsn(m5, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", getShort, "(Ljava/lang/String;)S", false));
		m4.name = getInt;
		m5.name = getInt;
		m6.name = getInt;
		m4.desc = getIntDesc;
		m5.desc = getIntDesc;
		m6.desc = getIntDesc;
	}

	/**
	 * increase limit of packet global entity id to integer
	 */
	private void patchS0FPacketSpawnMob(ClassNode node)
	{
		MethodNode ctr = ASMHelper.getConstructor(node, "(Lnet/minecraft/entity/EntityLivingBase;)V");
		InsnNode cast = (InsnNode) ASMHelper.firstInsn(ctr, Opcodes.I2B);
		ctr.instructions.remove(cast);
		
		MethodNode write = ASMHelper.getMethod(node, new MCPSidedString("writePacketData","func_148840_b").toString(), "(Lnet/minecraft/network/PacketBuffer;)V");
		MethodInsnNode m1 = (MethodInsnNode) ASMHelper.firstMethodInsn(write, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/PacketBuffer", new MCPSidedString("writeByte", "writeByte").toString(), "(I)Lio/netty/buffer/ByteBuf;", false));
		m1.name = new MCPSidedString("writeVarIntToBuffer", "func_150787_b").toString();
		m1.desc = "(I)V";
		write.instructions.remove(ASMHelper.nextInsn(m1, Opcodes.POP));
		AbstractInsnNode bit = ASMHelper.firstInsn(write, Opcodes.IAND);
		write.instructions.remove(bit.getPrevious());
		write.instructions.remove(bit);
		
		MethodNode read = ASMHelper.getMethod(node, new MCPSidedString("readPacketData", "func_148837_a").toString(), "(Lnet/minecraft/network/PacketBuffer;)V");
		AbstractInsnNode bit2 = ASMHelper.firstInsn(read, Opcodes.IAND);
		MethodInsnNode m2 = (MethodInsnNode) bit2.getPrevious().getPrevious();
		m2.name = new MCPSidedString("readVarIntFromBuffer", "func_150792_a").toString();
		m2.desc = "()I";
		write.instructions.remove(bit2.getPrevious());
		write.instructions.remove(bit2);
	}

	private void patchPotionEffect(ClassNode node) 
	{
		//patch the writeCustomPotionEffectToNBT
		MethodNode write = ASMHelper.getMethod(node, new MCPSidedString("writeCustomPotionEffectToNBT", "func_82719_a").toString(), "(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;");
		String setInt = new MCPSidedString("setInteger", "func_74768_a").toString();
		String getInt = new MCPSidedString("getInteger", "func_74762_e").toString();
		String getByte = new MCPSidedString("getByte", "func_74771_c").toString();
		String descV = "(Ljava/lang/String;I)V";
		String descI = "(Ljava/lang/String;)I";
		
		InsnNode i1 = (InsnNode) ASMHelper.firstInsn(write, Opcodes.I2B);
		MethodInsnNode m1 = (MethodInsnNode) i1.getNext();
		InsnNode i2 = (InsnNode) ASMHelper.nextInsn(i1, Opcodes.I2B);
		MethodInsnNode m2 = (MethodInsnNode) i2.getNext();
		//patch the method calls
		m1.name = setInt;
		m1.desc = descV;
		m2.name = setInt;
		m2.desc = descV;
		//remove the typecasts
		write.instructions.remove(i1);
		write.instructions.remove(i2);
		
		//patch read
		MethodNode read = ASMHelper.getMethod(node, new MCPSidedString("readCustomPotionEffectFromNBT","func_82722_b").toString(), "(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/potion/PotionEffect;");
		MethodInsnNode compare = new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", getByte, "(Ljava/lang/String;)B", false);
		MethodInsnNode r1 = ASMHelper.firstMethodInsn(read, compare);
		MethodInsnNode r2 = ASMHelper.nextMethodInsn(r1, compare);
		r1.name = getInt;
		r1.desc = descI;
		r2.name = getInt;
		r2.desc = descI;
		
		int count = 0;
		for(LocalVariableNode a : read.localVariables)
		{
			if(a.desc.equals("B"))
			{
				a.desc = "I";
				count++;
				if(count == 2)
					break;
			}
		}
	}

	private void patchS1DPacketEntityEffect(ClassNode node) throws IOException 
	{
		ASMHelper.replaceClassNode(node, ASMHelper.getInputStream(ModReference.MODID, "S1DPacketEntityEffect"));
	}

	private void patchS1EPacketRemoveEntityEffect(ClassNode node) throws IOException 
	{
		ASMHelper.replaceClassNode(node, ASMHelper.getInputStream(ModReference.MODID, "S1EPacketRemoveEntityEffect"));
	}

	private void patchEntityList(ClassNode node) 
	{
		MethodNode addMapping = ASMHelper.getMethod(node, new MCPSidedString("addMapping", "func_75618_a").toString(), "(Ljava/lang/Class;Ljava/lang/String;I)V");
		ASMHelper.clearNextThrowable(addMapping, "java/lang/IllegalArgumentException");
	}

	private void patchEnchantment(ClassNode node) 
	{
		//change enchantment limit from 256 > enchantment limit
		MethodNode clinit = ASMHelper.getClassInit(node);
		AbstractInsnNode spot = ASMHelper.firstFieldInsn(clinit, new FieldInsnNode(Opcodes.PUTSTATIC, "net/minecraft/enchantment/Enchantment", new MCPSidedString("enchantmentsList", "field_77331_b").toString(), "[Lnet/minecraft/enchantment/Enchantment;")).getPrevious().getPrevious();
		clinit.instructions.insert(spot, ASMHelper.getPushInsn(RegistryIds.limitEnchantments + 1));
		clinit.instructions.remove(spot);
	}

	private void patchBiome(ClassNode node) 
	{
		//TODO:
	}

	private void patchPotion(ClassNode node) 
	{
		//extend potion id limit to signed byte(0-127) or short
		MethodNode clinit = ASMHelper.getClassInit(node);
		AbstractInsnNode spot = ASMHelper.firstFieldInsn(clinit, new FieldInsnNode(Opcodes.PUTSTATIC, "net/minecraft/potion/Potion", new MCPSidedString("potionTypes", "field_76425_a").toString(), "[Lnet/minecraft/potion/Potion;") ).getPrevious().getPrevious();
		clinit.instructions.insert(spot, ASMHelper.getPushInsn(RegistryIds.limitPotions + 1));
		clinit.instructions.remove(spot);
	}
	
	private void patchDatawatcher(ClassNode node) 
	{	
		patchWatcherWriteFull(node);
		patchWatcherWrite(node);
		patchWatcherAdd(node);
		String input = ASMHelper.getInputStream(ModReference.MODID, "DataWatcher"); //"assets/confighelper/asm/" + (ObfHelper.isObf ? "srg/" : "deob/") + "DataWatcher";
		ASMHelper.replaceMethod(node, input, new MCPSidedString("writeWatchableObjectToPacketBuffer", "func_151510_a").toString(), "(Lnet/minecraft/network/PacketBuffer;Lnet/minecraft/entity/DataWatcher$WatchableObject;)V");
		ASMHelper.replaceMethod(node, input, new MCPSidedString("readWatchedListFromPacketBuffer", "func_151508_b").toString(), "(Lnet/minecraft/network/PacketBuffer;)Ljava/util/List;");
	}
	
	private void patchWatcherWriteFull(ClassNode node) 
	{
		MethodNode func = ASMHelper.getMethod(node, new MCPSidedString("func_151509_a", "func_151509_a").toString(), "(Lnet/minecraft/network/PacketBuffer;)V");
		//inject line: buf.writeVarIntToBuffer(this.watchedObject.size());
		InsnList list2 = new InsnList();
		list2.add(new VarInsnNode(Opcodes.ALOAD, 1));
		list2.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list2.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/DataWatcher", new MCPSidedString("watchedObjects", "field_75695_b").toString(), "Ljava/util/Map;"));
		list2.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "size", "()I", true));
		list2.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/PacketBuffer", new MCPSidedString("writeVarIntToBuffer", "func_150787_b").toString(), "(I)V", false));
		AbstractInsnNode index = ASMHelper.firstMethodInsn(func, new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/concurrent/locks/Lock", "lock", "()V", true));
		func.instructions.insert(index, list2);
		//delete line: buf.writeByte(127);
		AbstractInsnNode point = ASMHelper.lastMethodInsn(func, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/PacketBuffer", "writeByte", "(I)Lio/netty/buffer/ByteBuf;", false));
		ASMHelper.removeLine(func, point);
	}

	private void patchWatcherWrite(ClassNode node)
	{
		MethodNode write = ASMHelper.getMethod(node, new MCPSidedString("writeWatchedListToPacketBuffer", "func_151507_a").toString(), "(Ljava/util/List;Lnet/minecraft/network/PacketBuffer;)V");
		//inject line: buf.writeVarIntToBuffer(list.size());
		InsnList list3 = new InsnList();
		list3.add(new VarInsnNode(Opcodes.ALOAD, 1));
		list3.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list3.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true));
		list3.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/PacketBuffer", new MCPSidedString("writeVarIntToBuffer", "func_150787_b").toString(), "(I)V", false));
		write.instructions.insert(ASMHelper.firstInsn(write), list3);
		//delete line buf.writeByte(127)
		AbstractInsnNode point2 = ASMHelper.lastMethodInsn(write, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/PacketBuffer", "writeByte", "(I)Lio/netty/buffer/ByteBuf;", false));
		ASMHelper.removeLine(write, point2);
	}
	
	private void patchWatcherAdd(ClassNode node) 
	{
		MethodNode addObject = ASMHelper.getMethod(node, new MCPSidedString("addObject", "func_75682_a").toString(), "(ILjava/lang/Object;)V");
		
		//delete line Integer integer = (Integer) dataTypes.get(obj.getClass());
		AbstractInsnNode start = ASMHelper.firstFieldInsn(addObject, new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/entity/DataWatcher", new MCPSidedString("dataTypes", "field_75697_a").toString(), "Ljava/util/HashMap;"));
		AbstractInsnNode end = ASMHelper.nextInsn(start, Opcodes.ASTORE);
		ASMHelper.removeInsn(addObject, start, end);
		
		//inject line: Integer integer = Registries.getWatcherTypeId(obj.getClass());
		InsnList list = new InsnList();
		list.add(new VarInsnNode(Opcodes.ALOAD, 2));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "jml/confighelper/reg/Registries", "getWatcherTypeId", "(Ljava/lang/Class;)Ljava/lang/Integer;", false));
		list.add(new VarInsnNode(Opcodes.ASTORE, 3));
		addObject.instructions.insert(ASMHelper.firstInsn(addObject), list);
		
		//disable throwable if the id > 31
		JumpInsnNode todisable = null;
		IntInsnNode push = null;
		for(AbstractInsnNode ab : addObject.instructions.toArray())
		{
			if(ab.getOpcode() == Opcodes.BIPUSH)
			{
				push = (IntInsnNode)ab;
				todisable = ASMHelper.nextJumpInsn(push);
				break;
			}
		}
		InsnList append = new InsnList();
		append.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "jml/evilnotch/lib/asm/ASMHelper", "isFalse", "()Z", false));
		append.add(new JumpInsnNode(Opcodes.IFEQ, todisable.label));
		addObject.instructions.insertBefore(push.getPrevious(), append);
	}

	/**
	 * make it binarary compatible with the id extension change
	 */
	private void patchNetHandlerPlayClient(ClassNode node) 
	{ 
		MethodNode method = ASMHelper.getMethod(node, new MCPSidedString("handleEntityEffect", "func_147260_a").toString(), "(Lnet/minecraft/network/play/server/S1DPacketEntityEffect;)V");
		MethodInsnNode m1 = ASMHelper.firstMethodInsn(method, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/play/server/S1DPacketEntityEffect", "func_149427_e", "()B", false));
		MethodInsnNode m2 = ASMHelper.nextMethodInsn(m1, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/play/server/S1DPacketEntityEffect", "func_149425_g", "()S", false));
		MethodInsnNode m3 = ASMHelper.nextMethodInsn(m2, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/play/server/S1DPacketEntityEffect", "func_149428_f", "()B", false));
		m1.desc = "()I";
		m2.desc = "()I";
		m3.desc = "()I";
	}

}
