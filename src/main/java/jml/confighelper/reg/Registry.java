package jml.confighelper.reg;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

import jml.confighelper.RegistryConfig;
import jml.confighelper.datawatcher.WatcherDataType;
import jml.evilnotch.lib.JavaUtil;
import jml.evilnotch.lib.reflect.ReflectionHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

public class Registry implements Iterable<Registry.Entry>{

	public Map<Integer, List<Registry.Entry>> reg = new LinkedHashMap<Integer, List<Registry.Entry>>();
	public Set<Integer> vanillaIds = new HashSet();//the full list of vanilla ids per Registry
	public int limit;//the registry limit
	public int limitLower;
	public DataType dataType;//the data type this registry is for
	public boolean strict;//turn this on to automatically crash on the first sign of conflict
	public boolean hasConflicts;

	public Registry(DataType dataType)
	{
		this.dataType = dataType;
		this.vanillaIds = getVanillaIds();
		this.limit = getLimit();
		this.limitLower = this.getLimitLower();
	}

	protected int getLimitLower()
	{
		if(this.dataType == DataType.DIMENSION || this.dataType == DataType.PROVIDER)
			return RegistryIds.limitDimLower;
		return 0;
	}

	protected int getLimit()
	{
		if(dataType == DataType.BIOME)
			return RegistryIds.limitBiomes;
		else if(dataType == DataType.POTION)
			return RegistryIds.limitPotions;
		else if(dataType == DataType.ENCHANTMENT)
			return RegistryIds.limitEnchantments;
		else if(dataType == DataType.PROVIDER || dataType == DataType.DIMENSION)
			return RegistryIds.limitDim;
		else if(dataType == DataType.ENTITY)
			return RegistryIds.limitEntities;
		else if(dataType == DataType.DATAWATCHER)
			return RegistryIds.limitDatawatchers;
		return -1;
	}

	protected Set<Integer> getVanillaIds()
	{
		if(dataType == DataType.BIOME)
			return RegistryIds.biomes;
		else if(dataType == DataType.POTION)
			return RegistryIds.potions;
		else if(dataType == DataType.ENCHANTMENT)
			return RegistryIds.enchantments;
		else if(dataType == DataType.PROVIDER || dataType == DataType.DIMENSION)
			return RegistryIds.dimensions;
		else if(dataType == DataType.ENTITY)
			return RegistryIds.entities;
		else if(dataType == DataType.DATAWATCHER)
			return RegistryIds.datawatchers;
		return null;
	}

	public int reg(Object obj, int id)
	{
		if(Registries.isCrashing)
		{
			System.out.println("Unexpected Registration during crashing! " + id + ", " + obj + ", " + this.dataType);
			return id;
		}

		String clazz = getClass(obj).getName();
		boolean passable = this.isPassable(clazz, id);
		this.securityCheck();
		if(!passable)
			this.checkId(obj, id);//assume index out of bounds is a fake object
		List<Registry.Entry> list = this.getEntryOrg(id);
		if(list == null)
		{
			list = new ArrayList<Registry.Entry>();
			this.reg.put(id, list);
		}

		boolean conflicting = this.containsId(id);
		Registry.Entry entry = new Registry.Entry(obj, clazz, id);
		boolean contains = list.contains(entry);
		if(this.isPassableSelf(clazz) && contains)
		{
			Registry.Entry old = list.get(list.indexOf(entry));
			return old.newId;
		}
		else if(passable)
		{
			entry.replaced = true;
			if(!contains)
				list.add(entry);
			return entry.newId;
		}
		list.add(entry);

		if(conflicting)
		{
			entry.newId = this.getNewId(id);
			this.hasConflicts = true;
			if(this.canCrash())
			{
				Registries.isCrashing = true;
				this.grabNames(list);//grab the names and specify crashing early so the crash report prints out properly
				String cat = Registries.getCat();
				Registries.makeCrashReport(cat, this.dataType + " Id conflict during " +  cat + " id:" + id + "=" + list.toString());
			}
		}
		return entry.newId;
	}

	public void securityCheck()
	{
		int size = this.sizeLimit();
		Object[] arr = this.getStaticArray();
		if(arr != null && arr.length != size)
		{
		    System.out.println("Patching " + this.dataType + "[] as it's size doesn't match:\t" + arr.length + " > " + size);
		    Class arrClass = ReflectionHandler.getArrayClass(arr);
			Object[] newArray = (Object[]) Array.newInstance(arrClass, size);
			for(Registry.Entry e : this)
			{
				System.out.println("assigning obj:" + e.obj + " > " + e.newId);
				newArray[e.newId] = arrClass.cast(e.obj);
			}
			this.setStaticArray(newArray);
		}
	}

	private void setStaticArray(Object[] newArray)
	{
		if(this.dataType == DataType.BIOME)
			BiomeGenBase.biomeList = (BiomeGenBase[]) newArray;
		else if(this.dataType == DataType.POTION)
			Potion.potionTypes = (Potion[]) newArray;
		else if(this.dataType == DataType.ENCHANTMENT)
			Enchantment.enchantmentsList = (Enchantment[]) newArray;
	}

	public Object[] getStaticArray()
	{
		if(this.dataType == DataType.BIOME)
			return BiomeGenBase.biomeList;
		else if(this.dataType == DataType.POTION)
			return Potion.potionTypes;
		else if(this.dataType == DataType.ENCHANTMENT)
			return Enchantment.enchantmentsList;
		return null;
	}

	public int sizeLimit()
	{
		return this.limit + 1;
	}

	/**
	 * returns all the array sizes
	 */
	public int size()
	{
		int size = 0;
		for(List<Registry.Entry> li : this.reg.values())
			size += li.size();
		return size;
	}

	public void checkId(Object obj, int id)
	{
		if(id < this.limitLower || id > this.limit)
			Registries.makeCrashReport(Registries.getCat(), this.dataType + " ids must be between " + this.limitLower + "-" + this.limit + " id:" + id + ", " + getClass(obj).getName());
	}

	/**
	 * get used org Ids in order from least to greatest
	 */
	public Set<Integer> getOrgIds()
	{
		return new TreeSet(this.reg.keySet());
	}

	public Set<Integer> getNewIds()
	{
		Set<Integer> newIds = new TreeSet<Integer>();
		for(Registry.Entry entry : this)
				newIds.add(entry.newId);
		return newIds;
	}

	public boolean isPassableSelf(String clazz)
	{
		return JavaUtil.contains(RegistryConfig.passableSelf, clazz);
	}

	public boolean isPassable(String clazz, int id)
	{
		return JavaUtil.contains(RegistryConfig.passable, clazz);
	}

	public int getNewId()
	{
		return this.getNewId(1);
	}

	public int newId;//the newId(semi-auto) index
	public int newIdLower = -1;
	/**
	 * gets the next id
	 */
	public int getNewId(int org)
	{
		boolean positive = org >= 0;
		if(positive)
		{
			for(int i = this.newId; i <= this.limit; i++)
			{
				if(!this.containsId(this.newId) && !this.isVanillaId(this.newId))
				{
					return this.newId++;
				}
				this.newId++;
			}
		}
		else
		{
			for(int i = this.newIdLower; i >= this.limitLower; i--)
			{
				if(!this.containsId(this.newIdLower) && !this.isVanillaId(this.newIdLower))
				{
					return this.newIdLower--;
				}
				this.newIdLower--;
			}
		}
		Registries.makeCrashReport(Registries.getCat(), this.dataType + " Registry has run out of Ids(" + (positive ? "positive": "negative") + ") Id Range: " + this.limitLower + "-" + this.limit);
		return -1;
	}

	public int freeId;
	public int freeIdLower = -1;
	public int getNextFreeId(int org)
	{
		boolean positive = org >= 0;
		if(positive)
		{
			for(int i = this.freeId; i <= this.limit; i++)
			{
				if(!this.containsOrg(this.freeId))
				{
					return this.freeId++;
				}
				this.freeId++;
			}
		}
		else
		{
			for(int i = this.freeIdLower; i >= this.limitLower; i++)
			{
				if(!this.containsOrg(this.freeIdLower))
				{
					return this.freeIdLower--;
				}
				this.freeIdLower--;
			}
		}
		return -1;
	}

	public int suggestedId;//the virtual suggested id index
	public int suggestedIdLower = -1;
	public int getNextSuggestedId(int org)
	{
		boolean positive = org >= 0;
		if(positive)
		{
			for(int i = this.suggestedId; i <= this.limit; i++)
			{
				if(!this.isVanillaId(this.suggestedId))
				{
					return this.suggestedId++;
				}
				this.suggestedId++;
			}
		}
		else
		{
			for(int i = this.suggestedIdLower; i >= this.limitLower; i++)
			{
				if(!this.isVanillaId(this.suggestedIdLower))
				{
					return this.suggestedIdLower--;
				}
				this.suggestedIdLower--;
			}
		}
		return -1;
	}

	public void resetSuggestedIds()
	{
		this.suggestedId = 0;
		this.suggestedIdLower = -1;
	}

	public void resetFreeIds()
	{
		this.freeId = 0;
		this.freeIdLower = -1;
	}

	/**
	 * resets the suggested ids and the free ids
	 */
	public void resetInfoIds()
	{
		this.resetSuggestedIds();
		this.resetFreeIds();
	}

	public boolean containsOrg(int org)
	{
		return this.reg.containsKey(org);
	}

	/**
	 * a live look to see if the id is in memory
	 */
	public boolean containsId(int newId)
	{
		for(Registry.Entry entry : this)
		{
			if(entry.newId == newId)
				return true;
		}
		return false;
	}

	public boolean isVanillaId(int id)
	{
		return this.vanillaIds.contains(id);
	}

	/**
	 * returns whether or not it should crash on the first sign of conflict
	 */
	public boolean canCrash()
	{
		return this.strict;
	}

	public Class getClass(Object entry)
	{
		if(entry instanceof EntryEntity)
			return ((EntryEntity)entry).clazz;
		else if(entry instanceof EntryDimension)
			return Integer.class;
		else if(entry instanceof Class)
			return (Class) entry;
		return entry.getClass();
	}

	/**
	 * is the original requested id conflicting with another original conflicted id
	 */
	public boolean isConflicting(int org)
	{
		return this.getEntryOrg(org).size() > 1;
	}

	/**
	 * returns an array of entries that are linked to the original requested id.
	 * if there is more then one it is conflicting
	 */
	public List<Registry.Entry> getEntryOrg(int org)
	{
		return this.reg.get(org);
	}

	/**
	 * get a registry entry based on newId
	 */
	public Registry.Entry getEntry(int newId)
	{
		for(Registry.Entry e : this)
			if(e.newId == newId)
				return e;
		return null;
	}

	/**
	 * get the original id from the newId
	 */
	public int getOrg(int newId)
	{
		return getEntry(newId).org;
	}

	/**
	 * get a list view of the Registry that can be sorted without messing with indexes here
	 */
	public List<Entry> getSortable()
	{
		List<Registry.Entry> list = new ArrayList(this.size());
		for(Registry.Entry entry : this)
			list.add(entry);
		return list;
	}

	/**
	 * unregisters all ids attached to the newId
	 * if there is an intended conflict the newId will equal the org id and will occur multiple times
	 */
	public void unreg(int newId)
	{
		Iterator<Registry.Entry> it = this.iterator();
		while(it.hasNext())
		{
			Registry.Entry e = it.next();
			if(e.newId == newId)
			{
				it.remove();
				return;
			}
		}
	}

   	public String getDisplay(Registry.Entry e)
	{
   		return this.getDisplay(e, false);
	}

   	public String getDisplay(Registry.Entry e, boolean name)
	{
		return "(" + (e.replaced ? "replaced:" + e.replaced + ", " : "") + "name:" + e.name + ", " + (name ? e.modName + ", " : "") + e.clazz + ", orgId:" + e.org + ")";
	}

   	/**
   	 * grabs the registry names and mod names of the Registry.Entry objects
   	 */
   	public void grabNames()
   	{
   		for(Registry.Entry e : this)
   		{
   			this.grabNames(e);
   		}
   	}

	public void grabNames(List<Registry.Entry> list)
	{
		for(Registry.Entry e : list)
		{
			this.grabNames(e);
		}
	}

   	public void grabNames(Registry.Entry e)
   	{
		e.setName(this.getName(e));
		e.setModName();
	}

	protected String getName(Registry.Entry entry)
	{
		try
		{
			if(this.dataType == DataType.BIOME)
			{
				return ((BiomeGenBase)entry.obj).biomeName;
			}
			else if(this.dataType == DataType.POTION)
			{
				return ((Potion)entry.obj).getName();
			}
			else if(this.dataType == DataType.ENCHANTMENT)
			{
				return ((Enchantment)entry.obj).getName();
			}
			else if(this.dataType == DataType.PROVIDER)
			{
				WorldProvider provider = (WorldProvider) ((Class)entry.obj).newInstance();
				try
				{
					int dimOrgId = Registries.guessDimOrgId(entry.newId);
					provider.setDimension(dimOrgId);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
				return provider.getDimensionName();
			}
			else if(this.dataType == DataType.DIMENSION)
			{
				return Registries.dimensions.isVanillaId(entry.newId) ? "vanilla" : "modded";
			}
			else if(this.dataType == DataType.ENTITY)
			{
				return ((EntryEntity)entry.obj).name;
			}
			else if(this.dataType == DataType.DATAWATCHER)
			{
				return Registries.datawatchers.isVanillaId(entry.newId) ? "vanilla" : "modded";
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return null;
	}

    public static enum DataType{
    	BIOME(),
    	ENCHANTMENT(),
    	POTION(),
    	DIMENSION(),
    	PROVIDER(),
    	ENTITY(),
    	DATAWATCHER(),
    	DATAWATCHERTYPE(),
    	ITEM(),
    	BLOCK(),
    	TILEENTITY(),
    	RECIPES(),
    	GUI();

		public String getName()
		{
			return this.getName(true);
		}

		public String getName(boolean plural)
		{
			String str = this.toString().toLowerCase();
			if(plural)
			{
				if(this == DataType.ENTITY)
					str = str.substring(0, str.length()-1) + "ies";
				else
					str += "s";
			}
			return str;
		}
    }

    public static class Entry
    {
    	public Object obj;//the object to register
    	public int org;//the original id
    	public int newId;//the id in memory
    	public boolean replaced;//if it replaces an index in memory
    	public String clazz;// the class of the object may be wrong if it's a wrapper class
    	public String name;//display name
    	public String modName;//display mod name

    	public Entry(Object obj, String c, int org)
    	{
    		this.obj = obj;
    		this.clazz = c;
    		this.org = org;
    		this.newId = org;
    	}

    	public void setName(String name)
    	{
    		this.name = "" + name;
    	}

		public void setModName()
		{
			this.modName = Registries.getModName(this.getDataTypeClass());
		}

    	public String getDataTypeClass()
    	{
			if(this.obj instanceof BiomeGenBase)
			{
				return ((BiomeGenBase)this.obj).getBiomeClass().getName();
			}
			return this.clazz;
		}

		@Override
    	public String toString()
    	{
    		return "(name:" + this.name + ", newId:" + this.newId + ", class:" + this.clazz + ", " + this.modName + ")";
    	}

    	@Override
    	public boolean equals(Object obj)
    	{
    		if(!(obj instanceof Entry))
    			return false;
    		Entry e = (Entry)obj;
    		return this.org == e.org && this.clazz.equals(e.clazz);
    	}

    	@Override
    	public int hashCode()
    	{
    		return Objects.hashCode(this.org, this.clazz);
    	}
    }

    @Override
    public String toString()
    {
    	return this.reg.toString();
    }

	@Override
	public Iterator<Registry.Entry> iterator()
	{
		return Iterables.concat(this.reg.values()).iterator();
	}
}
