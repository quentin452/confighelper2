package jml.evilnotch.lib.simple;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import jml.evilnotch.lib.JavaUtil;

public class Directory extends java.io.File{

	protected File parent = JavaUtil.getParentFile(this);
	public Directory(File parent, String child) 
	{
		super(parent, child);
	}
	
	public Directory(String parent, String child) 
	{
		super(parent, child);
	}
	
	public Directory(String str)
	{
		super(str);
	}
	
	public Directory(URI uri)
	{
		super(uri);
	}
	
	@Override
	public boolean mkdir()
	{
		return super.mkdir();
	}
	
	@Override
	public boolean mkdirs()
	{
		return this.parent.exists() ? super.mkdir() : super.mkdirs();
	}
	
	public Directory create()
	{
		this.mkdirs();
		return this;
	}
	
	@Override
	public boolean isDirectory() throws SecurityException
	{
		return true;
	}
	
	@Override
	public boolean createNewFile()
	{
		return this.mkdirs();
	}

}
