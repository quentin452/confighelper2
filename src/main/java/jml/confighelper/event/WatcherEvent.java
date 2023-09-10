package jml.confighelper.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jml.confighelper.RegistryConfig;
import jml.confighelper.reg.Registries;

public class WatcherEvent {

    private static boolean checked;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void checkReg(EntityConstructing event) {
        if (!checked && event.entity instanceof EntityPlayer) {
            checked = true;
            if (RegistryConfig.configMode && !Registries.hasWatcherConflicts()) {
                Registries.writeWatcher();
            }
            if (Registries.hasWatcherConflicts()) {
                Registries.makeCrashReport(
                    "Constructing DataWatcher",
                    "DataWatcher Id Conflicts have been detected! Reconfigure your modpack",
                    true);
            }
            Registries.strictWatcher();
        }
    }
}
