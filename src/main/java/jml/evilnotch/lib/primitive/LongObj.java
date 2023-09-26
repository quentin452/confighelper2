package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

@SuppressWarnings("serial")
public class LongObj extends Number implements IModNumber{
	
	public long value;
	
	public LongObj(long l){
		this.value = l;
	}

	@Override
	public double doubleValue() {
		return (double)this.value;
	}

	@Override
	public float floatValue() {
		return (float)this.value;
	}

	@Override
	public int intValue() {
		return JavaUtil.castInt(this.value);
	}

	@Override
	public long longValue() {
		return this.value;
	}
	@Override
	public byte byteValue(){
		return JavaUtil.castByte(this.value);
	}
	@Override
	public short shortValue(){
		return JavaUtil.castShort(this.value);
	}

	@Override
	public void set(Number other)
	{
		this.value = JavaUtil.castLong(other);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Number))
			return false;
		return this.value == JavaUtil.castLong((Number)obj);
	}
	
	@Override 
	public String toString(){
		return "" + this.longValue();
	}
	@Override
	public int hashCode(){
		return Long.hashCode(this.value);
	}

}
