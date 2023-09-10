package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

/**
 * a modifyable integer object that can convert not just cast between other primitive values
 * 
 * @author jredfox
 */
@SuppressWarnings("serial")
public class IntObj extends Number implements IModNumber {

    public int value;

    public IntObj(int i) {
        this.value = i;
    }

    @Override
    public double doubleValue() {
        return (double) this.value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return (long) this.value;
    }

    @Override
    public byte byteValue() {
        return JavaUtil.castByte(this.value);
    }

    @Override
    public short shortValue() {
        return JavaUtil.castShort(this.value);
    }

    @Override
    public void set(Number other) {
        this.value = JavaUtil.castInt(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) return false;
        return this.value == JavaUtil.castInt((Number) obj);
    }

    @Override
    public String toString() {
        return "" + this.intValue();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

}
