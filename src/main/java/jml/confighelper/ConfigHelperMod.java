package jml.confighelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import jml.confighelper.event.WatcherEvent;
import jml.confighelper.reg.Registries;

@Mod(
    modid = ModReference.MODID,
    version = ModReference.VERSION,
    name = ModReference.NAME,
    dependencies = "required-after:evilnotchlib")
public class ConfigHelperMod {

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new WatcherEvent());
        // force load classes
        Enchantment e = Enchantment.aquaAffinity;
        DimensionManager.init();
        EntityCreeper creeper = new EntityCreeper(null);
    }

    /**
     * once the game has completely initialized output suggested ids for all mods
     */
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        Registries.loadComplete();
        Registries.nextDimFrozen = Registries.nextDim;
        Registries.setCat("Load Complete");
        if (RegistryConfig.configMode && !Registries.hasConflicts()) {
            Registries.write();
        }
        if (Registries.hasConflicts()) {
            Registries.makeCrashReport(
                Registries.getCat(),
                "Id Conflicts have been detected! Reconfigure your modpack:" + Registries.getConflictTypes());
        }
        Registries.strictRegs();
        Registries.setCat("In Game");
    }
}
