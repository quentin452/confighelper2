package jml.confighelper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

import jml.evilnotch.lib.asm.ASMHelper;

public class RegistryConfig {

    // mod config
    public static boolean configMode = true;
    public static boolean suggestIdChunks = true;
    public static boolean dumpIds;
    public static boolean regUnregBiomes = true;
    public static boolean autoBiomeMutated = true;

    public static int limitPotions = Short.MAX_VALUE;
    public static int limitEnchantments = Short.MAX_VALUE;
    public static int limitBiomes = 65536;

    // passable ids
    public static String[] passable = new String[] { "chylex.hee.world.biome.BiomeGenHardcoreEnd",
        "chylex.hee.world.WorldProviderHardcoreEnd", "chylex.hee.entity.mob.EntityMobEnderman",
        "chylex.hee.entity.block.EntityBlockEnderCrystal", "org.blockartistry.mod.DynSurround.data.FakeBiome",
        "mods.railcraft.common.carts.EntityCartCommand", "mods.railcraft.common.carts.EntityCartBasic",
        "mods.railcraft.common.carts.EntityCartChest", "mods.railcraft.common.carts.EntityCartFurnace",
        "mods.railcraft.common.carts.EntityCartTNT", "net.minecraft.world.biome.BiomeGenForest$2",
        "net.minecraft.world.biome.BiomeGenForest$1", "net.minecraft.world.biome.BiomeGenMutated",
        "net.minecraft.world.biome.BiomeGenTaiga", "net.minecraft.world.biome.BiomeGenHills",
        "net.minecraft.world.biome.BiomeGenSavanna$Mutated", "net.minecraft.world.biome.BiomeGenMesa",
        "net.minecraft.world.biome.BiomeGenPlains", "net.minecraft.world.biome.BiomeGenSnow",
        "net.minecraft.world.biome.BiomeGenForest" };
    public static String[] passableSelf = new String[] { "net.aetherteam.aether.dungeons.worldgen.DungeonsBiome",
        "danger.orespawn.BiomeGenUtopianPlains", "net.minecraft.entity.item.EntityMinecartHopper" };
    public static Set<Integer> passableDimIds;
    public static Set<Integer> passableWatcherIds;
    // optimizations
    public static boolean unloadModDimIds = true;

    static {
        Configuration cfg = new Configuration(new File(ASMHelper.getConfig(), "confighelper/main.cfg"));
        cfg.load();
        configMode = cfg.getBoolean(
            "configMode",
            "general",
            configMode,
            "disable this when your modpack has been configured properly so it runs faster");
        regUnregBiomes = cfg.getBoolean(
            "regUnregBiomes",
            "general",
            regUnregBiomes,
            "will prevent future biome conflicts if un registerd biomes get registerd later");
        dumpIds = cfg.getBoolean("dumpIds", "general", dumpIds, "dump original requested and memory indexed ids");
        suggestIdChunks = cfg
            .getBoolean("suggestIdChunks", "general", suggestIdChunks, "disable this to veiw more details");
        autoBiomeMutated = cfg.getBoolean(
            "autoBiomeMutated",
            "general",
            autoBiomeMutated,
            "if enabled client must have the same mods and loading order");

        passable = cfg.getStringList(
            "conflicts",
            "passable",
            passable,
            "passable Classes that are allowed to conflict(replace) a registry object");
        passableSelf = cfg.getStringList(
            "selfConflicts",
            "passable",
            passableSelf,
            "passable Classes that are allowed to conflict with itself");
        passableDimIds = getPassableIds(
            cfg,
            "conflictDimIds",
            "passable Dim ids(Not Provider) that are allowed to conflict. Only use if inputting the provider conflict class wasn't enough");
        passableWatcherIds = getPassableIds(
            cfg,
            "conflictWatcherIds",
            "passable ids that data watchers are allowed to conflict with");

        unloadModDimIds = cfg.getBoolean("unloadModDimIds", "optimization", unloadModDimIds, "enabled: (less laggy)");

        limitPotions = cfg.get("limit", "potions", limitPotions, "Potion[] capacity 0-" + Integer.MAX_VALUE)
            .getInt();
        limitEnchantments = cfg
            .get("limit", "enchantments", limitEnchantments, "Enchantment[] capacity 0-" + Short.MAX_VALUE)
            .getInt();
        limitBiomes = cfg.get("limit", "biomes", limitBiomes, "Biome[] capacity 0-" + 65536)
            .getInt();
        cfg.save();
    }

    private static Set<Integer> getPassableIds(Configuration cfg, String name, String comment) {
        String[] list = cfg.getStringList(name, "passable", new String[0], comment);
        Set<Integer> passable = new HashSet();
        for (int i = 0; i < list.length; i++) {
            try {
                passable.add(Integer.parseInt(list[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return passable;
    }

}
