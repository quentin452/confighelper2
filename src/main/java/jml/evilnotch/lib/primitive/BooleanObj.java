package jml.evilnotch.lib.primitive;

public class BooleanObj{
	
	public boolean value;
	
	public BooleanObj()
	{
		this(false);
	}
	
	public BooleanObj(boolean value)
	{
		this.value = value;
	}
	
	public void setValue(boolean value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return "" + this.value;
	}
	
	@Override
	public int hashCode()
	{
		return Boolean.hashCode(this.value);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Boolean)
		{
			Boolean b = (Boolean)obj;
			return this.value == b.booleanValue();
		}
		else if(obj instanceof BooleanObj)
		{
			return this.value == ((BooleanObj)obj).value;
		}
		return false;
	}
	
	public boolean booleanValue()
	{
		return this.value;
	}

}
