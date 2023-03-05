package jml.evilnotch.lib.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public interface Coremod extends IFMLLoadingPlugin{
	
	public void registerTransformers();

	@Override
	public default String[] getASMTransformerClass() {return null;}

	@Override
	public default String getModContainerClass() {return null;}

	@Override
	public default String getSetupClass() {return null;}

	@Override
	public default void injectData(Map<String, Object> data) {}

	@Override
	public default String getAccessTransformerClass() {return null;}
	
}
