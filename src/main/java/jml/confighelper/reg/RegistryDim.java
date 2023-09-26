package jml.confighelper.reg;

import java.util.Set;

import jml.confighelper.RegistryConfig;
import jml.confighelper.reg.Registry.DataType;
import net.minecraftforge.common.DimensionManager;

public class RegistryDim extends RegistryInt{
	
	public RegistryDim()
	{
		super(DataType.DIMENSION);
	}
	
	@Override
	public Set<Integer> getPassableIds()
	{
		return RegistryConfig.passableDimIds;
	}

}
