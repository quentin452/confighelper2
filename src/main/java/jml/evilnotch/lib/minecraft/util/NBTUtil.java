package jml.evilnotch.lib.minecraft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtil {

    public static NBTTagCompound getNBTTagCompound(String str) {
        return (NBTTagCompound) getNBT(str);
    }

    public static NBTTagList getNBTTagList(String str) {
        return (NBTTagList) getNBT(str);
    }

    public static NBTBase getNBT(String str) {
        try {
            return JsonToNBT.func_150315_a(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NBTTagCompound readNBT(File f) {
        try {
            return CompressedStreamTools.readCompressed(new FileInputStream(f));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void writeNBT(NBTTagCompound nbt, File f) {
        try {
            CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(f));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
