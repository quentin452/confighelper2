package jml.evilnotch.lib.line;

import java.util.ArrayList;
import java.util.List;

public class Section {
	
	public Character start;
	public Character end;
	public Character lquote;//be null if you don't want to support quotes
	public Character rquote;//be null if you don't want to support quotes
	
	public Section(Character start, Character end, char lquote, char rquote)
	{
		this.start = start;
		this.end = end;
		this.lquote = lquote;
		this.rquote = rquote;
	}
	
	/**
	 * split string into sections. If the section doesn't exist it will be null inside of the String[] array
	 */
	public static String[] splitSections(String string, List<Section> sections)
	{
		String[] list = new String[sections.size()];
		int index = 0;
		int count = 0;
		for(Section section : sections)
		{
			int startIndex = indexOf(string, index, section.start, section.lquote, section.rquote);
			int endIndex = indexOf(string, startIndex, section.end, section.lquote, section.rquote);
			if(startIndex == -1 || endIndex == -1)
				continue;
			list[count++] = string.substring(startIndex, endIndex).trim();
			index = endIndex;
		}
		return list;
	}
	
	public static int indexOf(String string, int index, Character find, char lq, char rq)
	{
		if(find == null)
			return index;
		else if(index == -1)
			return -1;
		return -1;
	}

}
