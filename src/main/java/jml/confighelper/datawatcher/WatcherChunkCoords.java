package jml.confighelper.datawatcher;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;

public class WatcherChunkCoords extends WatcherDataType<ChunkCoordinates> {

    public WatcherChunkCoords() {
        super(new ResourceLocation("minecraft:chunkcoords"), ChunkCoordinates.class);
    }

    @Override
    public void write(PacketBuffer buf, ChunkCoordinates cc) throws IOException {
        buf.writeInt(cc.posX);
        buf.writeInt(cc.posY);
        buf.writeInt(cc.posZ);
    }

    @Override
    public ChunkCoordinates read(PacketBuffer buf) throws IOException {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        return new ChunkCoordinates(x, y, z);
    }

}
