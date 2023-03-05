package jml.confighelper.reg;

import java.util.HashSet;
import java.util.Set;

import jml.confighelper.reg.Registry.DataType;

public abstract class RegistryInt extends Registry{

	public RegistryInt(DataType dataType) 
	{
		super(dataType);
	}
	
	/**
	 * a list of integer passable ids that are acceptible to conflict with
	 * WARNGING THIS ALLOWS FOR NOT JUST REPLACEMENT CONFLICTS BUT, ACTUAL CONFLICTS PROCEDE WITH CAUTION
	 */
	public abstract Set<Integer> getPassableIds();
	
	@Override
	public boolean isPassable(String clazz, int id) 
	{
		return this.getPassableIds().contains((Integer)id);
	}
	
	@Override
   	public String getDisplay(Registry.Entry e, boolean name)
	{
   		return "(" + e.name + ", org:" + e.org + ")";
	}

}
