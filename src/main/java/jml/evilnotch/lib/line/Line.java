package jml.evilnotch.lib.line;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import jml.evilnotch.lib.JavaUtil;
import jml.evilnotch.lib.minecraft.util.NBTUtil;

/**
 * line library 3.0. Describe an Object in the use of a single line
 * 
 * @author jredfox
 */
public class Line extends AbstractLine {

    public char sep;
    public char lmeta;
    public char rmeta;
    public char lmetaJson;
    public char rmetaJSon;
    public char lquote;
    public char rquote;
    public char equals;
    public char comma;
    public List<Section> sections = new ArrayList(4);
    public static final String[] types = { "B", "S", "I", "L", "F", "D", "Z" };
    public static final String version = "3.0";

    /**
     * the default line parsing chars
     */
    public Line() {
        this('"', '"', ':', '<', '>', '{', '}', '=', ',');
    }

    /**
     * everything is customizable for parsing
     */
    public Line(char lquote, char rquote, char sep, char lmeta, char rmeta, char lmetaJson, char rmetaJson, char equals,
        char comma) {
        super();
        this.lquote = lquote;
        this.rquote = rquote;
        this.sep = sep;
        this.lmeta = lmeta;
        this.rmeta = rmeta;
        this.lmetaJson = lmetaJson;
        this.rmetaJSon = rmetaJson;
        this.equals = equals;
        this.comma = comma;
        this.meta = new ArrayList(2);
        this.values = new ArrayList(1);
        this.sections.add(new Section(null, null, this.lquote, this.rquote));
        this.sections.add(new Section(this.lmeta, this.rmeta, this.lquote, this.rquote));
        this.sections.add(new Section(this.lmetaJson, this.rmetaJSon, this.lquote, this.rquote));
        this.sections.add(new Section(this.equals, null, this.lquote, this.rquote));
    }

    /**
     * every Line Object extending must NOT call super(String) but instead call, super() or super(chars...)
     * as it will cause the object to parse before the subclass has a chance to construct
     */
    public Line(String str) {
        this();
        this.parse(str);
    }

    /**
     * every Line Object extending must NOT call super(String) but instead call, super() or super(chars...)
     * as it will cause the object to parse before the subclass has a chance to construct
     */
    public Line(String str, char lquote, char rquote, char sep, char lmeta, char rmeta, char lmetaJson, char rmetaJson,
        char equals, char comma) {
        this(lquote, rquote, sep, lmeta, rmeta, lmetaJson, rmetaJson, equals, comma);
        this.parse(str);
    }

    /**
     * called after construction of the line object
     */
    @Override
    public void parse(String str) {
        String[] sections = Section.splitSections(str, this.sections);
        this.parseId(sections[0]);
        this.parseMeta(sections[1]);
        this.parseJson(sections[2]);
        this.parseValues(sections[3]);
    }

    public void parseId(String idSection) {
        if (idSection == null) throw new RuntimeException("Section for line is null this should never happen!");
        if (idSection.startsWith("" + this.lquote)) {
            idSection = JavaUtil.parseQuotes(idSection, 0, this.lquote + "" + this.rquote);// filter out the quotes
        }
        if (idSection.contains("" + this.sep)) {
            String[] idSplit = JavaUtil.splitFirst(idSection, this.sep);
            this.domain = idSplit[0];
            this.path = idSplit[1];
        } else {
            this.path = idSection;
        }
        this.id = this.domain + this.path;
    }

    public void parseMeta(String metaStr) {
        if (metaStr == null) return;
        metaStr = JavaUtil.parseQuotes(metaStr, 0, this.lmeta + "" + this.rmeta);
        this.meta.add(this.parseValue(metaStr));
    }

    public void parseJson(String jsonStr) {
        if (jsonStr == null) return;
        NBTTagCompound json = NBTUtil.getNBTTagCompound(jsonStr);
        if (json == null) throw new IllegalArgumentException("invalid parsing for NBT:" + jsonStr);
        this.meta.add(json);
    }

    public void parseValues(String valuesStr) {
        if (valuesStr == null) return;
        valuesStr = valuesStr.substring(1);// remove the equals
        String[] values = JavaUtil.split(valuesStr, this.comma, this.lquote, this.rquote);
        for (String str : values) this.values.add(this.parseValue(str));
    }

    public Object parseValue(String str) {
        if (JavaUtil.isBoolean(str)) {
            return Boolean.parseBoolean(str.substring(0, str.length() - 1));
        } else if (JavaUtil.isNumber(str)) {
            str = str.toLowerCase();
            if (str.endsWith(types[0])) return Byte.parseByte(str.substring(0, str.length() - 1));
            else if (str.endsWith(types[1])) return Short.parseShort(str.substring(0, str.length() - 1));
            else if (str.endsWith(types[2])) return Integer.parseInt(str.substring(0, str.length() - 1));
            else if (str.endsWith(types[3])) return Long.parseLong(str.substring(0, str.length() - 1));
            else if (str.endsWith(types[4])) return Float.parseFloat(str.substring(0, str.length() - 1));
            else if (str.endsWith(types[5])) return Double.parseDouble(str.substring(0, str.length() - 1));
            else return Long.parseLong(str);
        } else if (str.startsWith("" + this.lquote)) {
            return JavaUtil.parseQuotes(str, 0, this.lquote + "" + this.rquote);
        }
        return str.trim();// asserts that the value is now a string and will keep it trimed
    }

    @Override
    public String getDomainDefault() {
        return "";
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(this.domain, this.path);
    }

    public Byte getMetaByte() {
        return (Byte) this.getMeta();
    }

    public Short getMetaShort() {
        return (Short) this.getMeta();
    }

    public Integer getMetaInt() {
        return (Integer) this.getMeta();
    }

    public Long getMetaLong() {
        return (Long) this.getMeta();
    }

    public Float getMetaFloat() {
        return (Float) this.getMeta();
    }

    public Double getMetaDouble() {
        return (Double) this.getMeta();
    }

    public Boolean getMetaBoolean() {
        return (Boolean) this.getMeta();
    }

    public String getMetaString() {
        return (String) this.getMeta();
    }

    public Object getMeta() {
        return this.meta.get(0);
    }

    public NBTTagCompound getNBT() {
        return (NBTTagCompound) this.meta.get(1);
    }

}
