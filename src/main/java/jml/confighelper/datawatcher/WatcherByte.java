package jml.confighelper.datawatcher;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherByte extends WatcherDataType<Byte> {

    public WatcherByte() {
        super(new ResourceLocation("minecraft:byte"), Byte.class);
    }

    @Override
    public void write(PacketBuffer buf, Byte b) {
        buf.writeByte(b);
    }

    @Override
    public Byte read(PacketBuffer buf) {
        return buf.readByte();
    }

}
