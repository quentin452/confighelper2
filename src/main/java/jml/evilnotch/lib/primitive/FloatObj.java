package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

public class FloatObj extends Number implements IModNumber{
	
	public float value;
	public FloatObj(float f){
		this.value = f;
	}

	@Override
	public double doubleValue() {
		return (double)this.value;
	}

	@Override
	public float floatValue() {
		return this.value;
	}

	@Override
	public int intValue() {
		return JavaUtil.castInt(this.value);
	}

	@Override
	public long longValue() {
		return JavaUtil.castLong(this.value);
	}
	
	@Override
	public byte byteValue(){
		return JavaUtil.castByte(this.value);
	}
	@Override
	public short shortValue()
	{
		return JavaUtil.castByte(this.value);
	}
	
	@Override
	public void set(Number other)
	{
		this.value = JavaUtil.castFloat(other);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Number))
			return false;
		return this.value == JavaUtil.castFloat((Number)obj);
	}
	
	@Override 
	public String toString(){
		return "" + this.floatValue();
	}
	@Override
	public int hashCode(){
		return Float.hashCode(this.value);
	}

}
