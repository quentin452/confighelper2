package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

public class ShortObj extends Number implements IModNumber{
	
	public short value;
	
	public ShortObj(short s){
		this.value = s;
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
		return (int)this.value;
	}

	@Override
	public long longValue() {
		return (long)this.value;
	}
	
	@Override
	public short shortValue(){
		return this.value;
	}
	
	@Override
	public byte byteValue(){
		return JavaUtil.castByte(this.value);
	}

	@Override
	public void set(Number num) {
		this.value = JavaUtil.castShort(num);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Number))
			return false;
		return this.value == JavaUtil.castShort((Number)obj);
	}
	
	@Override 
	public String toString(){
		return "" + this.shortValue();
	}
	@Override
	public int hashCode(){
		return Short.hashCode(this.value);
	}

}
