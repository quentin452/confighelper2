package jml.confighelper.datawatcher;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherShort extends WatcherDataType<Short> {

    public WatcherShort() {
        super(new ResourceLocation("minecraft:short"), Short.class);
    }

    @Override
    public void write(PacketBuffer buf, Short s) {
        buf.writeShort(s);
    }

    @Override
    public Short read(PacketBuffer buf) {
        return buf.readShort();
    }

}
