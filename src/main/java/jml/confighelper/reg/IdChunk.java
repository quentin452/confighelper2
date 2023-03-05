package jml.confighelper.reg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;

public class IdChunk {
	
	public int minId;
	public int maxId;
	
	public IdChunk(int min, int max)
	{
		this.minId = min;
		this.maxId = max;
	}
	
	/**
	 * used for configuring around ids rather then ranging through the ids
	 * the Set(Integer) needs to be ordered from least to greatest
	 */
	public static Set<IdChunk> configureAround(int min, int max, Set<Integer> ids)
	{
		Iterator<Integer> it = ids.iterator();
		Set<IdChunk> chunks = new LinkedHashSet();
		int minId = min;
		int maxId = max;
		while(it.hasNext())
		{
			int usedId = it.next();
			maxId = usedId - 1;
			if(maxId >= minId)
				chunks.add(new IdChunk(minId, maxId));
			minId = usedId + 1;//reset min id for the next use
		}
		if(minId <= max)
		{
			maxId = max;
			chunks.add(new IdChunk(minId, maxId));
		}
		return chunks;
	}
	
	/**
	 * the Set(Integer) needs to be ordered from least to greatest
	 */
	public static Set<IdChunk> configureRanges(Set<Integer> ids)
	{
		List<IdChunk> chunks = new ArrayList();
		int index;
		for(Integer i : ids)
		{
			index = chunks.size() - 1;
			if(index == -1)
			{
				chunks.add(new IdChunk(i, i));
				continue;
			}
			IdChunk chunk = chunks.get(index);
			if(chunk.maxId + 1 == i)
			{
				chunk.maxId++;
			}
			else
			{
				chunks.add(new IdChunk(i, i));
			}
		}
		return new LinkedHashSet(chunks);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(!(other instanceof IdChunk))
			return false;
		IdChunk c = (IdChunk)other;
		return this.minId == c.minId && this.maxId == c.maxId;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.minId, this.maxId);
	}
	
	@Override
	public String toString()
	{
		if(this.minId == this.maxId)
			return "id:(" + this.minId + ")";
		return "id:(" + this.minId + " - " + this.maxId + ")"; 
	}

}
