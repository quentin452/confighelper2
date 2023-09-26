package jml.confighelper.datawatcher;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherItemStack extends WatcherDataType<ItemStack>{

	public WatcherItemStack() 
	{
		super(new ResourceLocation("minecraft:itemstack"), ItemStack.class);
	}

	@Override
	public void write(PacketBuffer buf, ItemStack stack) throws IOException 
	{
		buf.writeItemStackToBuffer(stack);
	}

	@Override
	public ItemStack read(PacketBuffer buf) throws IOException 
	{
		return buf.readItemStackFromBuffer();
	}

}
