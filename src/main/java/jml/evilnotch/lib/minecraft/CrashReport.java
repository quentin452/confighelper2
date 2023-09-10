package jml.evilnotch.lib.minecraft;

import net.minecraft.server.dedicated.DedicatedServer;

import jml.evilnotch.lib.mod.proxy.ClientProxy;
import jml.evilnotch.lib.mod.proxy.ServerProxy;

public class CrashReport {

    public static void makeCrashReport(String cat, String msg) {
        boolean isClient = false;
        try {
            Class c = DedicatedServer.class;
        } catch (Throwable t) {
            isClient = true;
        }
        if (isClient) {
            ClientProxy.makeCrashReport(cat, msg);
        } else {
            ServerProxy.makeCrashReport(cat, msg);
        }
    }
}
