package jml.confighelper.datawatcher;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import jml.confighelper.reg.CentralRegistry;
import jml.confighelper.reg.IRegisterable;

public abstract class WatcherDataType<T> implements IRegisterable {

    public int id = CentralRegistry.unset;
    public Class clazz;
    public ResourceLocation loc;

    public WatcherDataType(ResourceLocation loc, Class clazz) {
        this.loc = loc;
        this.clazz = clazz;
    }

    public abstract void write(PacketBuffer buf, T object) throws IOException;

    public abstract T read(PacketBuffer buf) throws IOException;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WatcherDataType)) return false;
        return this.id == ((WatcherDataType) other).id;
    }

    @Override
    public int hashCode() {
        return ((Integer) this.id).hashCode();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.loc;
    }

    @Override
    public void setRegistryName(ResourceLocation loc) {
        this.loc = loc;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
