package jml.evilnotch.lib.reflect;

import jml.evilnotch.lib.asm.ObfHelper;

public class MCPSidedString {
	
	public String deob;
	public String ob;
	
	public MCPSidedString(String deob, String ob)
	{
		this.deob = deob;
		this.ob = ob;
	}
	
	@Override
	public String toString()
	{
		return ObfHelper.isObf ? this.ob : this.deob;
	}

}
