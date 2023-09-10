package jml.confighelper.datawatcher;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherFloat extends WatcherDataType<Float> {

    public WatcherFloat() {
        super(new ResourceLocation("minecraft:float"), Float.class);
    }

    @Override
    public void write(PacketBuffer buf, Float f) {
        buf.writeFloat(f);
    }

    @Override
    public Float read(PacketBuffer buf) {
        return buf.readFloat();
    }

}
