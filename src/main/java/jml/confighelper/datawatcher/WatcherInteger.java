package jml.confighelper.datawatcher;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherInteger extends WatcherDataType<Integer> {

    public WatcherInteger() {
        super(new ResourceLocation("minecraft:integer"), Integer.class);
    }

    @Override
    public void write(PacketBuffer buf, Integer i) {
        buf.writeInt(i);
    }

    @Override
    public Integer read(PacketBuffer buf) {
        return buf.readInt();
    }

}
