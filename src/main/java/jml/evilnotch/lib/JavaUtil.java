package jml.evilnotch.lib;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import jml.evilnotch.lib.json.JSONObject;
import jml.evilnotch.lib.json.serialize.JSONSerializer;
import jml.evilnotch.lib.primitive.ByteObj;
import jml.evilnotch.lib.primitive.DoubleObj;
import jml.evilnotch.lib.primitive.FloatObj;
import jml.evilnotch.lib.primitive.IntObj;
import jml.evilnotch.lib.primitive.LongObj;
import jml.evilnotch.lib.primitive.ShortObj;
import jml.evilnotch.lib.reflect.ReflectionHandler;

public class JavaUtil {

    public static final String SPECIALCHARS = "~!@#$%^&*()_+`'-=/,.<>?\"{}[]:;|" + "\\";
    public static final String numberIds = "bsilfd";
    public static final int arrayInitCapacity = 10;
    public static final int fileCharLimit = 200;// since math path is 260 we want to be realitivly safe here
    public static final float FLOAT_MIN = Float.MAX_VALUE * -1;
    public static final float FLOAT_MAX = Float.MAX_VALUE;
    public static final double DOUBLE_MIN = Double.MAX_VALUE * -1;
    public static final double DOUBLE_MAX = Double.MAX_VALUE;

    public static double castDouble(double value) {
        return value;
    }

    public static double castDouble(float value) {
        return (double) value;
    }

    public static double castDouble(long value) {
        return (double) value;
    }

    public static double castDouble(int value) {
        return (double) value;
    }

    public static double castDouble(short value) {
        return (short) value;
    }

    public static double castDouble(byte value) {
        return (double) value;
    }

    // floats
    public static float castFloat(double value) {
        return (float) range(value, FLOAT_MIN, FLOAT_MAX);
    }

    public static float castFloat(float value) {
        return value;
    }

    public static float castFloat(long value) {
        return (float) value;
    }

    public static float castFloat(int value) {
        return (float) value;
    }

    public static float castFloat(short value) {
        return (float) value;
    }

    public static float castFloat(byte value) {
        return (float) value;
    }

    // longs
    public static long castLong(double value) {
        return (long) range(value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static long castLong(float value) {
        return (long) range(value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static long castLong(long value) {
        return value;
    }

    public static long castLong(int value) {
        return (long) value;
    }

    public static long castLong(short value) {
        return (long) value;
    }

    public static long castLong(byte value) {
        return (long) value;
    }

    // ints
    public static int castInt(double value) {
        return (int) range(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int castInt(float value) {
        return (int) range(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int castInt(long value) {
        return (int) range(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int castInt(int value) {
        return value;
    }

    public static int castInt(short value) {
        return (int) value;
    }

    public static int castInt(byte value) {
        return (int) value;
    }

    // shorts
    public static short castShort(double value) {
        return (short) range(value, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static short castShort(float value) {
        return (short) range(value, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static short castShort(long value) {
        return (short) range(value, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static short castShort(int value) {
        return (short) range(value, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static short castShort(short value) {
        return value;
    }

    public static short castShort(byte value) {
        return (short) value;
    }

    // bytes
    public static byte castByte(double value) {
        return (byte) range(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public static byte castByte(float value) {
        return (byte) range(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public static byte castByte(long value) {
        return (byte) range(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public static byte castByte(int value) {
        return (byte) range(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public static byte castByte(short value) {
        return (byte) range(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public static byte castByte(byte value) {
        return value;
    }

    public static double castDouble(Number obj) {
        return obj.doubleValue();
    }

    public static float castFloat(Number obj) {
        if (obj instanceof Double) return castFloat(obj.doubleValue());
        return obj.floatValue();
    }

    public static long castLong(Number obj) {
        obj = castWholeNumber(obj);
        return obj.longValue();
    }

    public static int castInt(Number obj) {
        obj = castWholeNumber(obj);
        if (obj instanceof Long) return JavaUtil.castInt(obj.longValue());
        return obj.intValue();
    }

    public static short castShort(Number obj) {
        obj = castWholeNumber(obj);
        if (obj instanceof Long) return JavaUtil.castShort(obj.longValue());
        else if (obj instanceof Integer) return JavaUtil.castShort(obj.intValue());
        return obj.shortValue();
    }

    public static byte castByte(Number obj) {
        obj = castWholeNumber(obj);
        if (obj instanceof Long) return JavaUtil.castByte(obj.longValue());
        else if (obj instanceof Integer) return JavaUtil.castByte(obj.intValue());
        else if (obj instanceof Short) return JavaUtil.castByte(obj.shortValue());
        return obj.byteValue();
    }

    /**
     * if double/float convert to integer of long else do nothing
     */
    public static Number castWholeNumber(Number obj) {
        if (isDouble(obj)) obj = new Long(JavaUtil.castLong(obj.doubleValue()));
        else if (isFloat(obj)) obj = new Long(JavaUtil.castLong(obj.floatValue()));
        return obj;
    }

    public static boolean isFloat(Number num) {
        return num instanceof Float || num instanceof FloatObj;
    }

    public static boolean isDouble(Number num) {
        return num instanceof Double || num instanceof DoubleObj;
    }

    public static double range(double num, double min, double max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static float range(float num, float min, float max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static long range(long num, long min, long max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static int range(int num, int min, int max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static short range(short num, short min, short max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static byte range(byte num, byte min, byte max) {
        if (num < min) num = min;
        else if (num > max) num = max;
        return num;
    }

    public static char range(char c, char min, char max) {
        if (c < min) c = min;
        else if (c > max) c = max;
        return c;
    }

    /**
     * dynamically get your current public ip address I recommend caching it somewhere so it doesn't go throw a huge
     * process each time
     */
    public static String getIpPublic() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        String ip = in.readLine(); // you get the IP as a String
        return ip.trim();
    }

    /**
     * your current computer adress's ip
     */
    public static String getIpv4() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

    public static boolean isOnline() {
        return isOnline("www.google.com");
    }

    public static boolean isOnline(String url) {
        try {
            if (url.startsWith("http")) throw new IllegalArgumentException("isOnline Requires non HTTP/HTTPS protical");
            Socket soc = new Socket();
            InetSocketAddress adress = new InetSocketAddress(url, 80);
            soc.setSoTimeout(3500);
            soc.connect(adress);
            soc.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void getDirFiles(File dir, Set<File> files, String ext, boolean blackList) {
        for (File file : dir.listFiles()) {
            boolean isType = blackList ? (!file.getName()
                .endsWith(ext))
                : (file.getName()
                    .endsWith(ext) || ext.equals("*"));
            if (file.isFile() && isType) {
                files.add(file);
            } else if (file.isDirectory()) {
                getDirFiles(file, files, ext, blackList);
            }
        }
    }

    public static void getDirFiles(File dir, Set<File> files) {
        getDirFiles(dir, files, "*", false);
    }

    public static Set<File> getDirFiles(File dir) {
        Set<File> files = new HashSet();
        getDirFiles(dir, files);
        return files;
    }

    public static Set<File> getDirFiles(File dir, String ext) {
        return getDirFiles(dir, ext, false);
    }

    public static Set<File> getDirFiles(File dir, String ext, boolean blackList) {
        Set<File> files = new HashSet();
        getDirFiles(dir, files, ext, blackList);
        return files;
    }

    public static void deleteDir(File dir) {
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(
                "file is not a directory! " + dir.getAbsoluteFile()
                    .toString());// sanity check
        }
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * supports alpha
     */
    public static Color getColor(int color) {
        int red = (int) (color >> 16 & 255);
        int green = (int) (color >> 8 & 255);
        int blue = (int) (color & 255);
        int alpha = (int) (color >> 24 & 255);
        return new Color(red, green, blue, alpha);
    }

    /**
     * converts rgba into a single integer
     */
    public static int getRGB(Color c) {
        return getRGB(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    /**
     * converts rgba into a single integer
     */
    public static int getRGB(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    /**
     * convert float(between 0.0F-1.0F) to int hex colors
     */
    public static Color getColor(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    public static float[] getSRGB(Color c) {
        return getSRGB(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    /**
     * get the Float(0.0F-1.0F) color values from int RGBA
     */
    public static float[] getSRGB(int r, int g, int b, int a) {
        float red = r / 255.0F;
        float green = g / 255.0F;
        float blue = b / 255.0F;
        float alpha = a / 255.0F;
        return new float[] { red, green, blue, alpha };
    }

    public static Color multiplyColor(int color, int colorMult) {
        return multiplyColor(getColor(color), getColor(colorMult));
    }

    /**
     * doesn't multiply alpha
     */
    public static Color multiplyColor(Color color, Color colorMult) {
        float[] cols = getSRGB(color);
        float[] mul = getSRGB(colorMult);
        float r = multiplyColor(cols[0], mul[0]);
        float g = multiplyColor(cols[1], mul[1]);
        float b = multiplyColor(cols[2], mul[2]);
        float a = cols[3];
        return new Color(r, g, b, a);
    }

    public static float multiplyColor(float color, float mult) {
        return range(color * mult, 0.0F, 1.0F);
    }

    public static boolean isSpecialChar(char c) {
        return SPECIALCHARS.contains("" + c);
    }

    /**
     * allows for white spacing
     */
    public static boolean isAlphanumeric(String str) {
        str = whiteSpaced(str);
        for (char c : str.toCharArray()) if (!isAlphanumeric(c)) return false;
        return true;
    }

    /**
     * allows for white spacing
     */
    public static boolean containsAlphanumeric(String str) {
        str = whiteSpaced(str);
        for (char c : str.toCharArray()) if (isAlphanumeric(c)) return true;
        return false;
    }

    /**
     * Ejects a string that is whitespaced
     */
    public static String whiteSpaced(String s) {
        return s.replaceAll("\\s+", "");
    }

    /**
     * doesn't support Unicode use int codepoints from a string instead
     */
    public static boolean isAlphanumeric(char c) {
        return isAlphanumeric((int) c);
    }

    /**
     * doesn't support Unicode use int codepoints from a string instead
     */
    public static boolean isLetterNumeric(char c) {
        return isLetterNumeric((int) c);
    }

    public static char toUpperCase(char c) {
        return Character.toUpperCase(c);
    }

    public static char toLowerCase(char c) {
        return Character.toLowerCase(c);
    }

    /**
     * doesn't support non english Alphabetical letters is isLetterNumeric instead for unicode full support
     */
    public static boolean isAlphanumeric(int c) {
        return Character.isAlphabetic(c) || Character.isDigit(c);
    }

    public static boolean isLetterNumeric(int c) {
        return Character.isLetterOrDigit(c);
    }

    public static void moveFileFromJar(Class clazz, String input, File output, boolean replace) {
        if (output.exists() && !replace) {
            return;
        }
        try {
            InputStream stream = clazz.getResourceAsStream(input);
            FileOutputStream outputStream = new FileOutputStream(output);
            IOUtils.copy(stream, outputStream);
            stream.close();
            outputStream.close();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }

    public static void printTime(long time, String msg) {
        System.out.println(msg + (System.currentTimeMillis() - time) + "ms");
    }

    public static void copyClipboard(String s) {
        copyClipboard(s, null);
    }

    public static void copyClipboard(String s, ClipboardOwner owner) {
        Validate.nonNull(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit()
            .getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, owner);
    }

    /**
     * the array type cannot be casted out of Object[] use toArray(Collection col, Class clazz) instead
     */
    public static Object[] toArray(Collection col) {
        return toArray(col, Object.class);
    }

    public static <T> T[] toArray(Collection<T> col, Class<T> clazz) {
        T[] li = (T[]) Array.newInstance(clazz, col.size());
        int index = 0;
        for (T obj : col) {
            li[index++] = obj;
        }
        return li;
    }

    public static <T> List<T> toArray(T... arr) {
        List<T> list = new ArrayList(arr.length + arrayInitCapacity);
        for (T obj : arr) list.add(obj);
        return list;
    }

    /**
     * get a static array from a list of parameter objects
     */
    public static <T> T[] asArray(T... arr) {
        return arr;
    }

    public static <K, V> SortedMap<K, V> sortByKeys(Map<K, V> map) {
        return new TreeMap<K, V>(map);
    }

    public static <K, V> SortedMap<K, V> sort(Map<K, V> map, Comparator c) {
        TreeMap<K, V> ordered = new TreeMap<K, V>(c);
        ordered.putAll(map);
        return ordered;
    }

    public static <K, V> SortedMap<K, V> sortByValues(Map<K, V> map) {
        SortedMap m = sort(map, new Comparator<Map.Entry>() {

            @Override
            public int compare(Map.Entry e1, Map.Entry e2) {
                return ((Comparable) e1.getValue()).compareTo((Comparable) e2.getValue());
            }
        });
        return m;
    }

    /**
     * returns a SortedMap based on keySets
     */
    public static <K, V> SortedMap<K, V> newSortedMap() {
        return newSortedMap(false);
    }

    /**
     * create a SortedMap with sortByKeys or sortByValues
     */
    public static <K, V> SortedMap<K, V> newSortedMap(boolean sortByValues) {
        return sortByValues ? new TreeMap(new Comparator<Map.Entry>() {

            @Override
            public int compare(Map.Entry e1, Map.Entry e2) {
                return ((Comparable) e1.getValue()).compareTo((Comparable) e2.getValue());
            }
        }) : new TreeMap();
    }

    /**
     * Returns Cross platform File removing invalid characters from the File name and trims it to 200 characters
     */
    public static String toFileChars(String s) {
        String name = "";
        String invalid = "*/<>?\":|" + "\\";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String sub = s.substring(i, i + 1);
            if (!invalid.contains(sub)) {
                builder.append(sub);
            }
        }
        name = toWindowsNaming(builder.toString());
        if (name.isEmpty()) {
            return "failed";
        } else if (name.length() > fileCharLimit) {
            int dot = name.lastIndexOf('.');
            String ext = dot != -1 ? name.substring(dot, name.length()) : "";
            name = name.substring(0, fileCharLimit - ext.length()) + ext;
        }
        return name;
    }

    /**
     * append an "_" if the file is an invalid windows name and also removes "." or " " at the end of the file name
     */
    public static String toWindowsNaming(String str) {
        if (str.isEmpty()) return str;
        String[] parts = JavaUtil.splitFirst(str, '.');
        String check = parts[0];
        String rest = (parts.length > 1 ? "." + parts[1] : "");
        if (check.equalsIgnoreCase("CON") || check.equalsIgnoreCase("PRN")
            || check.equalsIgnoreCase("AUX")
            || check.equalsIgnoreCase("NUL")) {
            str = check + "_" + rest;
        } else {
            for (int j = 0; j < 10; j++) {
                String com = "COM" + j;
                String lpt = "LPT" + j;
                if (check.equalsIgnoreCase(com) || check.equalsIgnoreCase(lpt)) {
                    str = check + "_" + rest;
                    break;
                }
            }
        }
        str = trimEnd(str, ". ");
        return str;
    }

    /**
     * trim invalid endings from a string
     */
    public static String trimEnd(String str, String invalid) {
        int index = trimEndIndex(str, invalid);
        return str.substring(0, index);
    }

    /**
     * trim invalid startings from a string
     */
    public static String trimStart(String str, String invalid) {
        int index = trimStartIndex(str, invalid);
        return str.substring(index, str.length());
    }

    /**
     * trim invalid endings and invalid startings froma a string
     */
    public static String trim(String str, String invalid) {
        return str.substring(trimStartIndex(str, invalid), trimEndIndex(str, invalid));
    }

    /**
     * returns the index of the last char that isn't invalid
     */
    public static int trimEndIndex(String str, String invalid) {
        int index = 0;
        for (int i = str.length(); i > 0; i--) {
            String s = str.substring(i - 1, i);
            if (!invalid.contains(s)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * returns the index of the first char that isn't invalid
     */
    public static int trimStartIndex(String str, String invalid) {
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (!invalid.contains(s)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * override one list from another starting at a specified index to the origin and will append if it cannot override
     */
    public static void set(List org, List list2, int start) {
        for (int i = 0; i < list2.size(); i++) {
            Object entry = list2.get(i);
            int index = start + i;
            if (index < org.size()) {
                org.set(index, entry);
            } else {
                org.add(entry);
            }
        }
    }

    public static void remove(List list, int start, int end) {
        List<String> sub = list.subList(start, end);
        sub.clear();
    }

    public static int lastChar(String str, char character) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.substring(i, i + 1)
                .equals("" + character)) return i;
        }
        return -1;
    }

    /**
     * String[1] will be empty if there is no more string after the specified index
     */
    public static String[] splitAtIndex(String str, int index) {
        String[] list = new String[2];
        list[0] = str.substring(0, index);
        int index2 = index + 1;
        list[1] = index2 < str.length() ? str.substring(index2, str.length()) : "";
        return list;
    }

    public static String reverse(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            builder.append(s.substring(i, i + 1));
        }
        return builder.toString();
    }

    public static void reverse(List org) {
        Collections.reverse(org);
    }

    // TODO:
    public static void reverse(Map<?, ?> map) {

    }

    public static void reverOrder(List<?> list) {
        Collections.sort(list, Collections.reverseOrder());
    }

    // TODO:
    public static void reverseOrder(Map org) {
        SortedMap map = sort(org, Collections.reverseOrder());
        org.clear();
        Iterator<Map.Entry> it = map.entrySet()
            .iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            org.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Use for hashmaps for keys that override the .equals method this compares memory location
     */
    public static boolean containsMemoryKey(Map map, Object obj) {
        Iterator it = map.keySet()
            .iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (obj == o) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsMemoryValue(Map map, Object obj) {
        Iterator it = map.values()
            .iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (obj == o) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullorEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static File getParentFile(File f) {
        return f.getAbsoluteFile()
            .getParentFile();
    }

    /**
     * Equivalent to Files.readAllLines() but, works way faster
     */
    public static List<String> getFileLines(File f) {
        return getFileLines(getReader(f));
    }

    public static List<String> getFileLines(String input) {
        return getFileLines(getReader(input));
    }

    public static List<String> getFileLines(BufferedReader reader) {
        List<String> list = null;
        try {
            list = new ArrayList();
            String s = reader.readLine();

            if (s != null) {
                list.add(s);
            }

            while (s != null) {
                s = reader.readLine();
                if (s != null) {
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Unable to Close InputStream this is bad");
                }
            }
        }
        return list;
    }

    /**
     * even though it's utf8 writing it's the fastes one I tried 5 different other options from different objects
     */
    public static BufferedWriter getWriter(File f) throws FileNotFoundException, IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));
    }

    public static BufferedReader getReader(File f) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
        } catch (Throwable t) {
            return null;
        }
    }

    public static BufferedReader getReader(String input) {
        return new BufferedReader(
            new InputStreamReader(
                JavaUtil.class.getClassLoader()
                    .getResourceAsStream(input)));
    }

    /**
     * Overwrites entire file default behavior no per line modification removal/addition
     */
    public static void saveFileLines(List<String> list, File f) {
        BufferedWriter writer = null;
        try {
            writer = JavaUtil.getWriter(f);
            for (String s : list) writer.write(s + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Unable to Close OutputStream this is bad");
                }
            }
        }
    }

    public static boolean isBoolean(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    public static boolean getBoolean(String str) {
        if (!isBoolean(str)) return false;
        return Boolean.parseBoolean(str);
    }

    public static boolean isURL(String url) {
        try {
            URL tst = new URL(url);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static JSONObject toJsonFrom64(String base64) {
        byte[] out = org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
        String str = new String(out, StandardCharsets.UTF_8);
        return new JSONObject(str);
    }

    public static long getDays(long ms) {
        return ms / (1000L * 60L * 60L * 24L);
    }

    public static long getTime(long time) {
        return System.currentTimeMillis() - time;
    }

    public static JSONObject getJSON(File file) {
        if (!file.exists()) return null;
        try {
            JSONSerializer parser = new JSONSerializer();
            return parser.readJSONObject(JavaUtil.getReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveJSONSafley(JSONObject json, File file) throws IOException {
        createFileSafley(file);
        saveJSON(json, file);
    }

    /**
     * @return if the file saved or not
     */
    public static boolean saveIfJSON(JSONObject json, File file) throws IOException {
        if (!file.exists()) {
            saveJSONSafley(json, file);
            return true;
        }
        return false;
    }

    public static void createFileSafley(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();
        if (!file.exists()) file.createNewFile();
    }

    public static void saveJSON(JSONObject json, File file) {
        try {
            BufferedWriter writer = JavaUtil.getWriter(file);
            JSONSerializer parser = new JSONSerializer();
            parser.setPrettyPrint(true);
            parser.write(json, writer);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getFirst(Collection li) {
        for (Object obj : li) return obj;
        return null;
    }

    public static Set asSet(Object[] obj) {
        Set set = new HashSet();
        for (Object o : obj) set.add(o);
        return set;
    }

    public static void populateStatic(Object[] locs, List names) {
        for (int i = 0; i < names.size(); i++) locs[i] = names.get(i);
    }

    public static boolean isClassExtending(Class<? extends Object> base, Class<? extends Object> toCompare) {
        return ReflectionHandler.instanceOf(base, toCompare);
    }

    /**
     * @return the char id based on the generic number object
     */
    public static char getNumId(Number obj) {
        if (obj instanceof Integer || obj instanceof IntObj) {
            return 'i';
        } else if (obj instanceof Long || obj instanceof LongObj) {
            return 'l';
        } else if (obj instanceof Short || obj instanceof ShortObj) {
            return 's';
        } else if (obj instanceof Byte || obj instanceof ByteObj) {
            return 'b';
        } else if (obj instanceof Float || obj instanceof FloatObj) {
            return 'f';
        } else if (obj instanceof Double || obj instanceof DoubleObj) {
            return 'd';
        }
        return ' ';
    }

    public static int findFirstChar(int index, String str, char c) {
        for (int j = index; j < str.length(); j++) if (str.charAt(j) == c) return j;
        return -1;
    }

    /**
     * get the id from the string to parse
     * 
     * @return ' ' if none is found
     */
    public static char getNumId(String str) {
        str = str.trim()
            .toLowerCase();
        String last = "" + str.charAt(str.length() - 1);
        if (numberIds.contains(last)) return last.toLowerCase()
            .charAt(0);
        return ' ';
    }

    /**
     * an optimized way to split a string from it's first instanceof a char
     */
    public static String[] splitFirst(String s, char reg) {
        String[] parts = new String[2];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == reg) {
                parts[0] = s.substring(0, i);
                parts[1] = s.substring(i + 1, s.length());
                break;
            }
        }
        if (parts[0] == null) return new String[] { s };
        return parts;
    }

    public static String parseQuotes(String s, int index, String q) {
        if (index == -1) return "";
        char lquote = q.charAt(0);
        char rquote = q.length() > 1 ? q.charAt(1) : lquote;

        String strid = "";
        int quote = 0;
        for (int i = index; i < s.length(); i++) {
            if (quote == 2) break; // if end of parsing object stop loop and return getParts(strid,":");
            char tocompare = s.charAt(i);
            boolean contains = tocompare == lquote && quote == 0 || tocompare == rquote;

            if (contains) quote++;
            if (!contains && quote > 0) strid += tocompare;
        }
        return strid;
    }

    public static boolean isStringInt(String s) {
        String valid = "1234567890-";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!valid.contains("" + c)) return false;
            else if (c == '-' && i > 0) return false;
        }
        return true;
    }

    public static boolean isNumber(String s) {
        String valid = "1234567890.-";
        String valid_endings = numberIds;// byte,short,long,float,double,int
        String check = ".";
        int indexdot = 0;
        if (s.indexOf('.') == 0 || s.indexOf('.') == s.length() - 1 || s.indexOf('-') > 0) return false;
        s = s.toLowerCase();
        for (int i = 0; i < s.length(); i++) {
            String character = s.substring(i, i + 1);
            boolean lastindex = i == s.length() - 1;
            if (check.contains(character)) {
                if (character.equals(".")) indexdot++;

                if (indexdot > 1) return false;
            }
            if (!valid.contains(character)) {
                return lastindex && valid_endings.contains(character);
            }
        }
        return true;
    }

    /**
     * create folders for file
     */
    public static void createFolders(File file) {
        File parent = file.getParentFile();
        if (!parent.exists()) file.getParentFile()
            .mkdirs();
    }

    public static List<String> asStringList(String[] str) {
        List list = new ArrayList(str.length);
        for (String s : str) list.add(s);
        return list;
    }

    public static List<String> asStringList(Class[] str) {
        List list = new ArrayList(str.length);
        for (Class s : str) list.add(s.getName());
        return list;
    }

    public static List<ResourceLocation> stringToLocArray(String[] list) {
        List<ResourceLocation> locs = new ArrayList();
        for (String str : list) locs.add(new ResourceLocation(str));
        return locs;
    }

    public static long[] arrayToStaticLong(List<Long> li) {
        long[] list = new long[li.size()];
        for (int i = 0; i < li.size(); i++) list[i] = li.get(i);
        return list;
    }

    public static int[] getStaticArrayInts(String str) {
        str = str.substring(1, str.length() - 1);
        String[] parts = str.split(",");
        int[] arr = new int[parts.length];
        int index = 0;
        for (String s : parts) {
            s = s.trim();
            arr[index] = Integer.parseInt(s);
            index++;
        }
        return arr;
    }

    public static boolean isStaticArray(Object value) {
        return value != null && value.getClass()
            .isArray();
    }

    public static boolean contains(String[] list, String name) {
        for (String s : list) if (name.equals(s)) return true;
        return false;
    }

    public static boolean equals(Class[] compare, Class[] params) {
        if (compare.length != params.length) return false;
        for (int i = 0; i < params.length; i++) {
            Class c1 = params[i];
            Class c2 = compare[i];
            if (!c1.equals(c2)) return false;
        }
        return true;
    }

    public static Class[] getWrappedClasses(Class[] params) {
        for (int i = 0; i < params.length; i++) {
            params[i] = getWrappedClass(params[i]);
        }
        return params;
    }

    public static Class getWrappedClass(Class clazz) {
        if (clazz.isPrimitive()) {
            if (boolean.class.equals(clazz)) return Boolean.class;
            else if (char.class.equals(clazz)) return Character.class;
            else if (byte.class.equals(clazz)) return Byte.class;
            else if (short.class.equals(clazz)) return Short.class;
            else if (int.class.equals(clazz)) return Integer.class;
            else if (long.class.equals(clazz)) return Long.class;
            else if (float.class.equals(clazz)) return Float.class;
            else if (double.class.equals(clazz)) return Double.class;
            else return null;// unkown data type
        }
        return clazz;
    }

    /**
     * combine two static arrays into one
     */
    public static <T> T[] concat(T[] a, T[] b) {
        T[] c = (T[]) Array.newInstance(
            a.getClass()
                .getComponentType(),
            a.length + b.length);
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * increase a static array by x amount of indices
     */
    public static <T> T[] increase(T[] src, int increment) {
        T[] arr = (T[]) Array.newInstance(
            src.getClass()
                .getComponentType(),
            src.length + increment);
        System.arraycopy(src, 0, arr, 0, src.length);
        return arr;
    }

    /**
     * split with quote ignoring support
     */
    public static String[] split(String str, char sep, char lquote, char rquote) {
        List<String> list = new ArrayList();
        boolean inside = false;
        for (int i = 0; i < str.length(); i += 1) {
            String a = str.substring(i, i + 1);
            String prev = str.substring(i - 1, i);
            boolean escape = prev.charAt(0) == '\\';
            if (a.equals("" + lquote) && !escape || a.equals("" + rquote) && !escape) {
                inside = !inside;
            }
            if (a.equals("" + sep) && !inside) {
                String section = str.substring(0, i);
                list.add(section);
                str = str.substring(i + ("" + sep).length(), str.length());
                i = -1;
            }
        }
        list.add(str);// add the rest of the string
        return JavaUtil.toArray(list, String.class);
    }

}
