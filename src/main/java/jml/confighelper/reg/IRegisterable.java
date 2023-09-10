package jml.confighelper.reg;

import net.minecraft.util.ResourceLocation;

public interface IRegisterable {

    public ResourceLocation getRegistryName();

    public void setRegistryName(ResourceLocation loc);

    /**
     * the value must return CentralRegistry.unset if it hasn't been set yet
     */
    public int getId();

    public void setId(int id);

}
