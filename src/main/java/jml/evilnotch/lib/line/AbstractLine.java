package jml.evilnotch.lib.line;

import java.util.List;

public abstract class AbstractLine {
	
	public String domain = this.getDomainDefault();
	public String path;
	public String id;
	public List<Object> meta;
	public List<Object> values;
	
	public AbstractLine()
	{
		
	}
	
	public abstract String getDomainDefault();
	public abstract void parse(String str);

}
