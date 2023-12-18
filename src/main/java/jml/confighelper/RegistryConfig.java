package jml.confighelper;

import jml.evilnotch.lib.asm.ASMHelper;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RegistryConfig {

	//mod config
	public static boolean configMode = true;
	public static boolean suggestIdChunks = true;
	public static boolean dumpIds;
	public static boolean regUnregBiomes = true;
	public static boolean autoBiomeMutated = true;

	public static int limitPotions = Short.MAX_VALUE;
	public static int limitEnchantments = Short.MAX_VALUE;
	public static int limitBiomes = 65536;

	//passable ids
	public static String[] passable = new String[]
	{
		"chylex.hee.world.biome.BiomeGenHardcoreEnd",
		"chylex.hee.world.WorldProviderHardcoreEnd",
		"chylex.hee.entity.mob.EntityMobEnderman",
		"chylex.hee.entity.block.EntityBlockEnderCrystal",
		"org.blockartistry.mod.DynSurround.data.FakeBiome",
		"mods.railcraft.common.carts.EntityCartCommand",
		"mods.railcraft.common.carts.EntityCartBasic",
		"mods.railcraft.common.carts.EntityCartChest",
		"mods.railcraft.common.carts.EntityCartFurnace",
		"mods.railcraft.common.carts.EntityCartTNT",
		"net.minecraft.world.biome.BiomeGenForest$2",
		"net.minecraft.world.biome.BiomeGenForest$1",
		"net.minecraft.world.biome.BiomeGenMutated",
		"net.minecraft.world.biome.BiomeGenTaiga",
		"net.minecraft.world.biome.BiomeGenHills",
		"net.minecraft.world.biome.BiomeGenSavanna$Mutated",
		"net.minecraft.world.biome.BiomeGenMesa",
		"net.minecraft.world.biome.BiomeGenPlains",
		"net.minecraft.world.biome.BiomeGenSnow",
		"net.minecraft.world.biome.BiomeGenForest",
        "lotr.common.world.biome.LOTRBiomeGenAdornland",
        "lotr.common.world.biome.LOTRBiomeGenAndrast",
        "lotr.common.world.biome.LOTRBiomeGenAnduin",
        "lotr.common.world.biome.LOTRBiomeGenAnduinMouth",
        "lotr.common.world.biome.LOTRBiomeGenAnduinVale",
        "lotr.common.world.biome.LOTRBiomeGenAngle",
        "lotr.common.world.biome.LOTRBiomeGenAngmar",
        "lotr.common.world.biome.LOTRBiomeGenAngmarMountains",
        "lotr.common.world.biome.LOTRBiomeGenBarrowDowns",
        "lotr.common.world.biome.LOTRBiomeGenBeach",
        "lotr.common.world.biome.LOTRBiomeGenBlackrootVale",
        "lotr.common.world.biome.LOTRBiomeGenBlueMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenBlueMountains",
        "lotr.common.world.biome.LOTRBiomeGenBreeland",
        "lotr.common.world.biome.LOTRBiomeGenBrownLands",
        "lotr.common.world.biome.LOTRBiomeGenCelebrant",
        "lotr.common.world.biome.LOTRBiomeGenChetwood",
        "lotr.common.world.biome.LOTRBiomeGenColdfells",
        "lotr.common.world.biome.LOTRBiomeGenDagorlad",
        "lotr.common.world.biome.LOTRBiomeGenDale",
        "lotr.common.world.biome.LOTRBiomeGenDeadMarshes",
        "lotr.common.world.biome.LOTRBiomeGenDolGuldur",
        "lotr.common.world.biome.LOTRBiomeGenDorEnErnilHills",
        "lotr.common.world.biome.LOTRBiomeGenDorEnErnil",
        "lotr.common.world.biome.LOTRBiomeGenDorwinionHills",
        "lotr.common.world.biome.LOTRBiomeGenDorwinion",
        "lotr.common.world.biome.LOTRBiomeGenDunland",
        "lotr.common.world.biome.LOTRBiomeGenEastBight",
        "lotr.common.world.biome.LOTRBiomeGenEasternDesolation",
        "lotr.common.world.biome.LOTRBiomeGenEmynMuil",
        "lotr.common.world.biome.LOTRBiomeGenEnedwaith",
        "lotr.common.world.biome.LOTRBiomeGenEntwashMouth",
        "lotr.common.world.biome.LOTRBiomeGenErebor",
        "lotr.common.world.biome.LOTRBiomeGenEregion",
        "lotr.common.world.biome.LOTRBiomeGenEriadorDowns",
        "lotr.common.world.biome.LOTRBiomeGenEriador",
        "lotr.common.world.biome.LOTRBiomeGenErynVorn",
        "lotr.common.world.biome.LOTRBiomeGenEttenmoors",
        "lotr.common.world.biome.LOTRBiomeGenFangornClearing",
        "lotr.common.world.biome.LOTRBiomeGenFangorn",
        "lotr.common.world.biome.LOTRBiomeGenFangornWasteland",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradArid",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradBushland",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradCloudForest",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradCoast",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradForest",
        "lotr.common.world.biome.LOTRBiomeGenFarHarad",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradJungleEdge",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradJungle",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradJungleLake",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradJungleMountains",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradMangrove",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradSwamp",
        "lotr.common.world.biome.LOTRBiomeGenFarHaradVolcano",
        "lotr.common.world.biome.LOTRBiomeGenForodwaithCoast",
        "lotr.common.world.biome.LOTRBiomeGenForodwaithGlacier",
        "lotr.common.world.biome.LOTRBiomeGenForodwaith",
        "lotr.common.world.biome.LOTRBiomeGenForodwaithMountains",
        "lotr.common.world.biome.LOTRBiomeGenGladdenFields",
        "lotr.common.world.biome.LOTRBiomeGenGondor",
        "lotr.common.world.biome.LOTRBiomeGenGondorWoodlands",
        "lotr.common.world.biome.LOTRBiomeGenGorgoroth",
        "lotr.common.world.biome.LOTRBiomeGenGreyMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenGreyMountains",
        "lotr.common.world.biome.LOTRBiomeGenGulfHaradForest",
        "lotr.common.world.biome.LOTRBiomeGenGulfHarad",
        "lotr.common.world.biome.LOTRBiomeGenHalfTrollForest",
        "lotr.common.world.biome.LOTRBiomeGenHaradMountains",
        "lotr.common.world.biome.LOTRBiomeGenHarnedor",
        "lotr.common.world.biome.LOTRBiomeGenHarondor",
        "lotr.common.world.biome.LOTRBiomeGenImlothMelui",
        "lotr.common.world.biome.LOTRBiomeGenIronHills",
        "lotr.common.world.biome.LOTRBiomeGenIthilienHills",
        "lotr.common.world.biome.LOTRBiomeGenIthilien",
        "lotr.common.world.biome.LOTRBiomeGenIthilienWasteland",
        "lotr.common.world.biome.LOTRBiomeGenKanuka",
        "lotr.common.world.biome.LOTRBiomeGenLake",
        "lotr.common.world.biome.LOTRBiomeGenLamedonHills",
        "lotr.common.world.biome.LOTRBiomeGenLamedon",
        "lotr.common.world.biome.LOTRBiomeGenLastDesert",
        "lotr.common.world.biome.LOTRBiomeGenLebennin",
        "lotr.common.world.biome.LOTRBiomeGenLindonCoast",
        "lotr.common.world.biome.LOTRBiomeGenLindon",
        "lotr.common.world.biome.LOTRBiomeGenLindonWoodlands",
        "lotr.common.world.biome.LOTRBiomeGenLoneLandsHills",
        "lotr.common.world.biome.LOTRBiomeGenLoneLands",
        "lotr.common.world.biome.LOTRBiomeGenLongMarshes",
        "lotr.common.world.biome.LOTRBiomeGenLossarnach",
        "lotr.common.world.biome.LOTRBiomeGenLostladen",
        "lotr.common.world.biome.LOTRBiomeGenLothlorienEdge",
        "lotr.common.world.biome.LOTRBiomeGenLothlorien",
        "lotr.common.world.biome.LOTRBiomeGenMeneltarma",
        "lotr.common.world.biome.LOTRBiomeGenMidgewater",
        "lotr.common.world.biome.LOTRBiomeGenMinhiriath",
        "lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted",
        "lotr.common.world.biome.LOTRBiomeGenMirkwood",
        "lotr.common.world.biome.LOTRBiomeGenMirkwoodMountains",
        "lotr.common.world.biome.LOTRBiomeGenMirkwoodNorth",
        "lotr.common.world.biome.LOTRBiomeGenMistyMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenMistyMountains",
        "lotr.common.world.biome.LOTRBiomeGenMordor",
        "lotr.common.world.biome.LOTRBiomeGenMordorMountains",
        "lotr.common.world.biome.LOTRBiomeGenMorgulVale",
        "lotr.common.world.biome.LOTRBiomeGenNanCurunir",
        "lotr.common.world.biome.LOTRBiomeGenNanUngol",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradFertileForest",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradFertile",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradHills",
        "lotr.common.world.biome.LOTRBiomeGenNearHarad",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradOasis",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradRed",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradRiverbank",
        "lotr.common.world.biome.LOTRBiomeGenNearHaradSemiDesert",
        "lotr.common.world.biome.LOTRBiomeGenNindalf",
        "lotr.common.world.biome.LOTRBiomeGenNurnen",
        "lotr.common.world.biome.LOTRBiomeGenNurn",
        "lotr.common.world.biome.LOTRBiomeGenNurnMarshes",
        "lotr.common.world.biome.LOTRBiomeGenOcean",
        "lotr.common.world.biome.LOTRBiomeGenOldForest",
        "lotr.common.world.biome.LOTRBiomeGenPelargir",
        "lotr.common.world.biome.LOTRBiomeGenPelennor",
        "lotr.common.world.biome.LOTRBiomeGenPertorogwaith",
        "lotr.common.world.biome.LOTRBiomeGenPinnathGelin",
        "lotr.common.world.biome.LOTRBiomeGenPukel",
        "lotr.common.world.biome.LOTRBiomeGenRedMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenRedMountains",
        "lotr.common.world.biome.LOTRBiomeGenRhunForest",
        "lotr.common.world.biome.LOTRBiomeGenRhunIslandForest",
        "lotr.common.world.biome.LOTRBiomeGenRhunIsland",
        "lotr.common.world.biome.LOTRBiomeGenRhun",
        "lotr.common.world.biome.LOTRBiomeGenRhunLandHills",
        "lotr.common.world.biome.LOTRBiomeGenRhunLand",
        "lotr.common.world.biome.LOTRBiomeGenRhunLandSteppe",
        "lotr.common.world.biome.LOTRBiomeGenRhunRedForest",
        "lotr.common.world.biome.LOTRBiomeGenRivendellHills",
        "lotr.common.world.biome.LOTRBiomeGenRivendell",
        "lotr.common.world.biome.LOTRBiomeGenRiver",
        "lotr.common.world.biome.LOTRBiomeGenRohan",
        "lotr.common.world.biome.LOTRBiomeGenRohanUruk",
        "lotr.common.world.biome.LOTRBiomeGenRohanWoodlands",
        "lotr.common.world.biome.LOTRBiomeGenShire",
        "lotr.common.world.biome.LOTRBiomeGenShireMarshes",
        "lotr.common.world.biome.LOTRBiomeGenShireMoors",
        "lotr.common.world.biome.LOTRBiomeGenShireWoodlands",
        "lotr.common.world.biome.LOTRBiomeGenSwanfleet",
        "lotr.common.world.biome.LOTRBiomeGenTaiga",
        "lotr.common.world.biome.LOTRBiomeGenTauredainClearing",
        "lotr.common.world.biome.LOTRBiomeGenTolfalas",
        "lotr.common.world.biome.LOTRBiomeGenTowerHills",
        "lotr.common.world.biome.LOTRBiomeGenTrollshaws",
        "lotr.common.world.biome.LOTRBiomeGenTundra",
        "lotr.common.world.biome.LOTRBiomeGenUdun",
        "lotr.common.world.biome.LOTRBiomeGenUmbarForest",
        "lotr.common.world.biome.LOTRBiomeGenUmbar",
        "lotr.common.world.biome.LOTRBiomeGenUtumno",
        "lotr.common.world.biome.LOTRBiomeGenWhiteDowns",
        "lotr.common.world.biome.LOTRBiomeGenWhiteMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenWhiteMountains",
        "lotr.common.world.biome.LOTRBiomeGenWilderland",
        "lotr.common.world.biome.LOTRBiomeGenWilderlandNorth",
        "lotr.common.world.biome.LOTRBiomeGenWindMountainsFoothills",
        "lotr.common.world.biome.LOTRBiomeGenWindMountains",
        "lotr.common.world.biome.LOTRBiomeGenWold",
        "lotr.common.world.biome.LOTRBiomeGenWoodlandRealmHills",
        "lotr.common.world.biome.LOTRBiomeGenWoodlandRealm",
	};
	public static String[] passableSelf = new String[]
	{
        "fr.iamacat.dangerzone_iamacatfr.worldgen.biomes.BiomeGenUtopianPlains",
		"net.aetherteam.aether.dungeons.worldgen.DungeonsBiome",
		"danger.orespawn.BiomeGenUtopianPlains",
		"net.minecraft.entity.item.EntityMinecartHopper",
        " appeng.spatial.StorageWorldProvider"
	};
	public static Set<Integer> passableDimIds;
	public static Set<Integer> passableWatcherIds;
	//optimizations
	public static boolean unloadModDimIds = true;

	static
	{
		Configuration cfg = new Configuration(new File(ASMHelper.getConfig(), "confighelper/main.cfg"));
		cfg.load();
		configMode = cfg.getBoolean("configMode", "general", configMode, "disable this when your modpack has been configured properly so it runs faster");
		regUnregBiomes = cfg.getBoolean("regUnregBiomes", "general", regUnregBiomes, "will prevent future biome conflicts if un registerd biomes get registerd later");
		dumpIds = cfg.getBoolean("dumpIds", "general", dumpIds, "dump original requested and memory indexed ids");
		suggestIdChunks = cfg.getBoolean("suggestIdChunks", "general", suggestIdChunks, "disable this to veiw more details");
		autoBiomeMutated = cfg.getBoolean("autoBiomeMutated", "general", autoBiomeMutated, "if enabled client must have the same mods and loading order");

		passable = cfg.getStringList("conflicts", "passable", passable, "passable Classes that are allowed to conflict(replace) a registry object");
		passableSelf = cfg.getStringList("selfConflicts", "passable", passableSelf, "passable Classes that are allowed to conflict with itself");
		passableDimIds = getPassableIds(cfg, "conflictDimIds", "passable Dim ids(Not Provider) that are allowed to conflict. Only use if inputting the provider conflict class wasn't enough");
		passableWatcherIds = getPassableIds(cfg, "conflictWatcherIds", "passable ids that data watchers are allowed to conflict with");

		unloadModDimIds = cfg.getBoolean("unloadModDimIds", "optimization", unloadModDimIds, "enabled: (less laggy)");

		limitPotions = cfg.get("limit", "potions", limitPotions, "Potion[] capacity 0-" + Integer.MAX_VALUE).getInt();
		limitEnchantments = cfg.get("limit", "enchantments", limitEnchantments, "Enchantment[] capacity 0-" + Short.MAX_VALUE).getInt();
		limitBiomes = cfg.get("limit", "biomes", limitBiomes, "Biome[] capacity 0-" + 65536).getInt();
		cfg.save();
	}

	private static Set<Integer> getPassableIds(Configuration cfg, String name, String comment)
	{
		String[] list = cfg.getStringList(name, "passable", new String[0], comment);
		Set<Integer> passable = new HashSet();
		for(int i=0;i<list.length;i++)
		{
			try
			{
				passable.add(Integer.parseInt(list[i]));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return passable;
	}

}
