package jml.evilnotch.lib.primitive;

import jml.evilnotch.lib.JavaUtil;

public class DoubleObj extends Number implements IModNumber {

    public double value;

    public DoubleObj(double d) {
        this.value = d;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return JavaUtil.castFloat(this.value);
    }

    @Override
    public int intValue() {
        return JavaUtil.castInt(this.value);
    }

    @Override
    public long longValue() {
        return JavaUtil.castInt(this.value);
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
        this.value = JavaUtil.castDouble(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) return false;
        return this.value == JavaUtil.castDouble((Number) obj);
    }

    @Override
    public String toString() {
        return "" + this.doubleValue();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.value);
    }

}
