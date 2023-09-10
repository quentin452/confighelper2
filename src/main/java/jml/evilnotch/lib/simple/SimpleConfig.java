package jml.evilnotch.lib.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import jml.evilnotch.lib.JavaUtil;

public class SimpleConfig {

    public Map<String, Object> list = new TreeMap<String, Object>();
    public File file;
    public char sep;
    public boolean spacedEnd;
    public static final String[] types = { "B", "S", "I", "L", "F", "D", "Z", "Str" };
    public static final String version = "1.0";

    public SimpleConfig(File f) {
        this(f, '=');
    }

    public SimpleConfig(File f, char sep) {
        this(f, sep, false);
    }

    public SimpleConfig(File f, char sep, boolean spacedEnd) {
        this.file = new File(f.getAbsolutePath());
        this.sep = sep;
        this.spacedEnd = spacedEnd;
    }

    /**
     * starting after this call the list will now be ordered
     */
    public void setOrdered() {
        this.list = new LinkedHashMap<String, Object>(this.list);
    }

    /**
     * although alphabitized by default calling setOrdered will not be alphabitized though
     */
    public void setAlphabitized() {
        this.list = new TreeMap<String, Object>(this.list);
    }

    public void setFile(File f) {
        this.file = f.getAbsoluteFile();
    }

    public Boolean getBoolean(String key, boolean init) {
        return (Boolean) get(key, init);
    }

    public Byte getByte(String key, byte init) {
        return (Byte) get(key, init);
    }

    public Short getShort(String key, short init) {
        return (Short) get(key, init);
    }

    public Integer getInt(String key, int init) {
        return (Integer) get(key, init);
    }

    public Long getLong(String key, long init) {
        return (Long) get(key, init);
    }

    public Float getFloat(String key, float init) {
        return (Float) get(key, init);
    }

    public Double getDouble(String key, double init) {
        return (Double) get(key, init);
    }

    public String getString(String key, String init) {
        return (String) get(key, init);
    }

    public void set(String key, Object value) {
        list.put(key, value);
    }

    public Object get(String key, Object init) {
        if (key.contains("" + this.sep)) throw new IllegalArgumentException("key contains invalid char of:" + this.sep);
        Object value = this.list.get(key);
        if (value == null) {
            list.put(key, init);
            return init;
        }
        return value;
    }

    public void clear() {
        this.list.clear();
    }

    public void load() {
        if (!this.file.exists()) return;
        this.clear();
        try {
            BufferedReader reader = JavaUtil.getReader(this.file);
            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.indexOf('#') == 0) continue;
                this.parseLine(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseLine(String line) {
        String[] reg = JavaUtil.splitFirst(line, ':');
        String type = reg[0].trim();
        reg = JavaUtil.splitFirst(reg[1], this.sep);
        String key = reg[0].trim();
        String value = reg[1].trim();
        Object parsed = this.parseObj(type, value);
        this.list.put(key, parsed);
    }

    public void save() {
        try {
            File parent = this.file.getParentFile();
            if (!parent.exists()) parent.mkdirs();
            BufferedWriter writer = JavaUtil.getWriter(this.file);
            writer.write("#build:" + version + "\r\n");
            for (String key : this.list.keySet()) {
                Object value = this.list.get(key);
                String type = this.getType(value);
                String strValue = value instanceof String ? "\"" + value + "\"" : value.toString();
                key = key.contains(" ") ? "\"" + key + "\"" : key;
                String equals = this.spacedEnd ? " " + this.sep + " " : "" + this.sep;
                writer.write(type + ":" + key + equals + strValue + "\r\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object parseObj(String type, String value) {
        if (types[0].equals(type)) return Byte.parseByte(value);
        else if (types[1].equals(type)) return Short.parseShort(value);
        else if (types[2].equals(type)) return Integer.parseInt(value);
        else if (types[3].equals(type)) return Long.parseLong(value);
        else if (types[4].equals(type)) return Float.parseFloat(value);
        else if (types[5].equals(type)) return Double.parseDouble(value);
        else if (types[6].equals(type)) return Boolean.parseBoolean(value);
        else if (types[7].equals(type)) return JavaUtil.parseQuotes(value, 0, "\"");

        return null;
    }

    public String getType(Object obj) {
        if (obj instanceof Byte) return types[0];
        else if (obj instanceof Short) return types[1];
        else if (obj instanceof Integer) return types[2];
        else if (obj instanceof Long) return types[3];
        else if (obj instanceof Float) return types[4];
        else if (obj instanceof Double) return types[5];
        else if (obj instanceof Boolean) return types[6];
        else if (obj instanceof String) return types[7];
        return null;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SimpleConfig)) return false;
        SimpleConfig cfg = (SimpleConfig) other;
        return this.list.equals(cfg.list);
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }

}
