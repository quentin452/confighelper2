package jml.evilnotch.lib.mod.proxy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.crash.CrashReport;

import cpw.mods.fml.common.FMLCommonHandler;

public class ServerProxy {

    public static void makeCrashReport(String cat, String msg) {
        CrashReport crashreport = CrashReport.makeCrashReport(new RuntimeException(msg), cat);
        crashreport.makeCategory(cat);
        makeCrashReport(crashreport);
    }

    private static void makeCrashReport(CrashReport crashreport) {
        File dir = new File(new File("."), "crash-reports");
        File crashFile = new File(
            dir,
            "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
        System.out.println(crashreport.getCompleteReport());

        int retVal;
        if (crashreport.saveToFile(crashFile)) {
            System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + crashFile.getAbsolutePath());
            retVal = -1;
        } else {
            System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            retVal = -2;
        }
        FMLCommonHandler.instance()
            .handleExit(retVal);
    }

}
