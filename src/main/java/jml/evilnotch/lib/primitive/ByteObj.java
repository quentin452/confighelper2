package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

public class ByteObj extends Number implements IModNumber{
	
	public byte value;
	
	public ByteObj(byte b)
	{
		this.value = b;
	}

	@Override
	public double doubleValue() 
	{
		return (double)this.value;
	}

	@Override
	public float floatValue()
	{
		return (float)this.value;
	}

	@Override
	public int intValue()
	{
		return (int)this.value;
	}

	@Override
	public long longValue() 
	{
		return (long)this.value;
	}
	
	@Override
	public short shortValue()
	{
		return (short)this.value;
	}
	
	@Override
	public byte byteValue()
	{
		return this.value;
	}
	
	@Override
	public void set(Number num)
	{
		this.value = JavaUtil.castByte(num);
	}
	
	/**
	 * even if it gets truncated the value wouldn't be equal since the value will have different signs so no extra work is needed besides default
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Number))
			return false;
		return this.value == JavaUtil.castByte((Number)obj);
	}
	
	@Override 
	public String toString(){
		return "" + this.byteValue();
	}
	
	@Override
	public int hashCode(){
		return Byte.hashCode(this.value);
	}

}
