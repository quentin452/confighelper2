package jml.confighelper.datawatcher;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WatcherString extends WatcherDataType<String> {

    public WatcherString() {
        super(new ResourceLocation("minecraft:string"), String.class);
    }

    @Override
    public void write(PacketBuffer buf, String str) throws IOException {
        buf.writeStringToBuffer(str);
    }

    @Override
    public String read(PacketBuffer buf) throws IOException {
        return buf.readStringFromBuffer(32767);
    }

}
