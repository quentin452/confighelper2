package jml.confighelper.reg;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * example of how the custom registry with a custom data type is suppose to work
 */
public abstract class RegistryCustom extends Registry{
	
	public RegistryCustom(DataType type, int limit)
	{
		this(type, limit, new HashSet());
	}

	public RegistryCustom(DataType type, int limit, Set<Integer> vanillaIds) 
	{
		super(type);
		this.limit = limit;
		this.vanillaIds = vanillaIds;
	}
	
	@Override
	protected abstract String getName(Registry.Entry entry);

}
