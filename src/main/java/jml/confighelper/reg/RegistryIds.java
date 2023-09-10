package jml.confighelper.reg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

import jml.confighelper.RegistryConfig;
import jml.confighelper.reg.Registry.DataType;
import jml.evilnotch.lib.reflect.ReflectionHandler;

public class RegistryIds {

    public static final String MCVERSION = "1.7.10";// what version do these vanilla ids support?
    public static final Set<Integer> biomes = new HashSet(
        asList(
            new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
                26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 129, 130, 131, 132, 133, 134, 140, 149, 151,
                155, 156, 157, 158, 160, 161, 162, 163, 164, 165, 166, 167 }));
    public static final Set<Integer> potions = new HashSet(
        asList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }));
    public static final Set<Integer> enchantments = new HashSet(
        asList(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48, 49, 50, 51, 61, 62 }));
    public static final Set<Integer> entities = new HashSet(
        asList(
            new int[] { 1, 2, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 40, 41, 42, 43, 44, 45, 46, 47,
                48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 200, 90, 91, 92, 93, 94, 95,
                96, 97, 98, 99, 100, 120 }));
    public static final Set<Integer> dimensions = new HashSet(asList(new int[] { -1, 0, 1 }));
    public static final Set<Integer> datawatchers = new HashSet(asList(new int[] { 0, 1, 6, 7, 8, 9, 16, 17, 18 }));
    public static final Set<Integer> datawatertypes = new HashSet(asList(new int[] { 0, 1, 2, 3, 4, 5, 6 }));

    public static int limitBiomes = RegistryConfig.limitBiomes;// 65536
    public static int limitPotions = RegistryConfig.limitPotions;// Short.MAX_VALUE;//127
    public static int limitEnchantments = RegistryConfig.limitEnchantments;// Short.MAX_VALUE;//255
    public static int limitEntities = Integer.MAX_VALUE;// 255
    public static int limitDatawatchers = Integer.MAX_VALUE;// 31
    public static int limitDatawatcherType = Integer.MAX_VALUE;// 6
    public static int limitDim = Integer.MAX_VALUE;// int
    public static int limitDimLower = Integer.MIN_VALUE;// int min

    public static void genIds() {
        try {
            System.out.println("biomes:" + getIntArray(getStaticReg(DataType.BIOME)));
            System.out.println("potions:" + getIntArray(getStaticReg(DataType.POTION)));
            System.out.println("enchantments:" + getIntArray(getStaticReg(DataType.ENCHANTMENT)));
            System.out.println("dimensions:" + getDims());
            System.out.println("entities:" + getEnts());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected static Object[] getStaticReg(DataType dataType) {
        if (dataType == DataType.BIOME) return BiomeGenBase.getBiomeGenArray();
        if (dataType == DataType.POTION) return Potion.potionTypes;
        if (dataType == DataType.ENCHANTMENT) return Enchantment.enchantmentsList;
        return null;
    }

    public static List asList(int[] arr) {
        List<Integer> ints = new ArrayList(arr.length);
        for (int i : arr) ints.add(i);
        return ints;
    }

    public static void genDatawatchers(EntityPlayer player) {
        try {
            System.out.println("datawatchers:" + getDatawatchers(player));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String getDatawatchers(EntityPlayer player) {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (Object i : player.getDataWatcher().watchedObjects.keySet()) {
            b.append(i + ",");
        }
        b.append(']');
        return b.toString();
    }

    private static String getEnts() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (Object i : EntityList.IDtoClassMapping.keySet()) b.append(i + ",");
        b.append(']');
        return b.toString();
    }

    private static String getDims() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        HashMap<Integer, Integer> dimensions = (HashMap<Integer, Integer>) ReflectionHandler
            .get(ReflectionHandler.getField(DimensionManager.class, "dimensions"), null);
        for (Integer i : dimensions.keySet()) b.append(i + ",");
        b.append(']');
        return b.toString();
    }

    private static String getIntArray(Object[] arr) {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int index = 0; index < arr.length; index++) {
            if (arr[index] != null) b.append(index + ",");
        }
        b.append(']');
        return b.toString();
    }

    public static List asList(Object[] li) {
        ArrayList list = new ArrayList(li.length);
        for (Object obj : li) list.add(obj);
        return list;
    }
}
