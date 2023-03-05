package jml.evilnotch.lib.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import jml.evilnotch.lib.JavaUtil;
import jml.evilnotch.lib.json.serialize.IGsonable;
import jml.evilnotch.lib.json.serialize.JSONSerializer;

/**
 * Main JSONArray object
 */
public class JSONArray extends ArrayList<Object> implements IGsonable{
	
	public JSONArray()
	{
		super();
	}
	
	public JSONArray(int capacity)
	{
		super(capacity);
	}

	public JSONArray(Collection collection)
	{
		super((Collection)JSONUtil.toJSONValue(collection));
	}
	
	public <T> JSONArray(T[] array) 
	{
		for(T element : array) {
			this.add(element);
		}
	}
	
	public JSONArray(byte[] array) 
	{
		for(byte element : array) 
		{
			this.add(element);
		}
	}
	
	public JSONArray(boolean[] array) 
	{	
		for(boolean element : array) 
		{	
			this.add(element);
		}
	}
	
	public JSONArray(char[] array) 
	{
		for(char element : array) 
		{
			this.add(element);
		}
	}
	
	public JSONArray(short[] array) 
	{	
		for(short element : array) 
		{	
			this.add(element);
		}
	}
	
	public JSONArray(int[] array) 
	{	
		for(int element : array) 
		{	
			this.add(element);
		}
	}
	
	public JSONArray(long[] array) 
	{	
		for(long element : array) 
		{	
			this.add(element);
		}
	}
	
	public JSONArray(float[] array) 
	{	
		for(float element : array) 
		{	
			this.add(element);
		}
	}
	
	public JSONArray(double[] array) 
	{	
		for(double element : array) 
		{		
			this.add(element);
		}
	}
	
	public JSONArray(String json) 
	{
		super(new JSONSerializer().readJSONArray(json));
	}
	
	public JSONArray(Reader reader) 
	{
		super(new JSONSerializer().readJSONArray(reader));
	}

	@Override
	public boolean add(Object obj)
	{
		obj = JSONUtil.toJSONValue(obj);
		return super.add(obj);
	}
	
	@Override
	public void add(int index, Object obj)
	{
		obj = JSONUtil.toJSONValue(obj);
		super.add(index, obj);
	}
	
	@Override
	public boolean addAll(Collection map)
	{
		map = (Collection) JSONUtil.toJSONValue(map);
		return super.addAll(map);
	}
	
	@Override
	public boolean addAll(int index, Collection map)
	{
		map = (Collection) JSONUtil.toJSONValue(map);
		return super.addAll(index, map);
	}
	
	public Long getLong(int key)
	{
		return JavaUtil.castLong((Number)this.get(key));
	}

	public Integer getInt(int key)
	{
		return JavaUtil.castInt((Number)this.get(key));
	}
	
	public Short getShort(int key)
	{
		return JavaUtil.castShort((Number)this.get(key));
	}
	
	public Byte getByte(int key)
	{
		return JavaUtil.castByte((Number)this.get(key));
	}
	
	public Double getDouble(int key)
	{
		return JavaUtil.castDouble((Number)this.get(key));
	}
	
	public Float getFloat(int key)
	{
		return JavaUtil.castFloat((Number)this.get(key));
	}
	
	public boolean getBoolean(int key)
	{
		return (Boolean) this.get(key);
	}
	
	public char getChar(int key)
	{
		return this.getString(key).charAt(0);
	}
	
	public String getString(int key)
	{
		return (String) this.get(key);
	}
	
	public JSONObject getJSONObject(int key)
	{
		return (JSONObject)this.get(key);
	}
	
	public JSONArray getJSONArray(int key)
	{
		return (JSONArray)this.get(key);
	}
	
	/**
	 * @param non primitive object arrays are converted into valid json arrays no recursion this converts data into one JSONArray
	 */
	public boolean addStaticArray(Object[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(long[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(int[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(short[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(byte[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(double[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(float[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(boolean[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	public boolean addStaticArray(char[] value)
	{
		return this.add(new JSONArray(value));
	}
	
	/**
	 * @param non primitive object arrays are converted into valid json arrays no recursion this converts data into one JSONArray
	 */
	public void addStaticArray(int index, Object[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, long[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, int[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, short[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, byte[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, double[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, float[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, boolean[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	public void addStaticArray(int index, char[] value)
	{
		this.add(index, new JSONArray(value));
	}
	
	/**
	 * @param non primitive object arrays are converted into valid json arrays no recursion this converts data into one JSONArray
	 */
	public void setStaticArray(int index, Object[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, long[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, int[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, short[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, byte[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, double[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, float[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, boolean[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public void setStaticArray(int index, char[] value)
	{
		this.set(index, new JSONArray(value));
	}
	
	public String[] getStringArray()
	{
		int size = this.size();
		String[] value = new String[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getString(index);
		}
		return value;
	}
	
	public long[] getLongArray()
	{
		int size = this.size();
		long[] value = new long[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getLong(index);
		}
		return value;
	}
	
	public int[] getIntArray()
	{
		int size = this.size();
		int[] value = new int[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getInt(index);
		}
		return value;
	}
	
	public short[] getShortArray() 
	{
		int size = this.size();
		short[] value = new short[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getShort(index);
		}
		return value;
	}
	
	public byte[] getByteArray() 
	{
		int size = this.size();
		byte[] value = new byte[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getByte(index);
		}
		return value;
	}
	
	public double[] getDoubleArray() 
	{
		int size = this.size();
		double[] value = new double[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getDouble(index);
		}
		return value;
	}
	
	public float[] getFloatArray()
	{
		int size = this.size();
		float[] value = new float[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getFloat(index);
		}
		return value;
	}
	
	public boolean[] getBooleanArray() 
	{
		int size = this.size();
		boolean[] value = new boolean[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getBoolean(index);
		}
		return value;
	}
	
	public char[] getCharArray() 
	{
		int size = this.size();
		char[] value = new char[size];
		for(int index=0;index<size;index++)
		{
			value[index] = this.getChar(index);
		}
		return value;
	}
	
	public String[] getStringArray(int key)
	{
		return this.getJSONArray(key).getStringArray();
	}
	
	public long[] getLongArray(int key)
	{
		return this.getJSONArray(key).getLongArray();
	}
	
	public int[] getIntArray(int key)
	{
		return this.getJSONArray(key).getIntArray();
	}
	
	public short[] getShortArray(int key)
	{
		return this.getJSONArray(key).getShortArray();
	}
	
	public byte[] getByteArray(int key)
	{
		return this.getJSONArray(key).getByteArray();
	}
	
	public double[] getDoubleArray(int key)
	{
		return this.getJSONArray(key).getDoubleArray();
	}
	
	public float[] getFloatArray(int key)
	{
		return this.getJSONArray(key).getFloatArray();
	}
	
	public boolean[] getBooleanArray(int key)
	{
		return this.getJSONArray(key).getBooleanArray();
	}
	
	public char[] getCharArray(int key)
	{
		return this.getJSONArray(key).getCharArray();
	}
	
	/**
	 * get a date
	 */
	public Date getDate(int key, DateFormat format) throws ParseException 
	{
		return format.parse(this.getString(key));
	}
	
	public void setDate(int key, Date date, DateFormat format)
	{
		this.set(key, format.format(key));
	}
	
	public void addDate(int key, Date date, DateFormat format)
	{
		this.add(key, format.format(date));
	}
	
	public boolean addDate(Date date, DateFormat format)
	{
		return this.add(format.format(date));
	}

	@Override
	public String toString() 
	{
		return new JSONSerializer().toPrettyPrint(this);
	}
	
	@Override
	public boolean equals(Object object) 
	{	
		if(!(object instanceof JSONArray)) 
			return false;
		
		JSONArray other = (JSONArray)object;
		int size = this.size();
		if(size == other.size()) 
		{					
			for(int index = 0; index < size; index++)
			{		
				Object entry1 = this.get(index);
				Object entry2 = other.get(index);
				if(entry1 == null && entry2 != null)
				{
					return false;
				}
				else if(!entry1.equals(entry2))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public JsonArray toGson()
	{
		JsonArray json = new JsonArray();
		for(Object value : this)
		{
			if(value instanceof JSONObject)
			{
				json.add(((JSONObject) value).toGson());
			}
			else if(value instanceof JSONArray)
			{
				json.add(((JSONArray) value).toGson());
			}
			else if(value == null)
			{
				json.add(JSONUtil.newGsonPrimitive(value));
			}
			else if(value instanceof String)
			{
				json.add(JSONUtil.newGsonPrimitive(value));
			}
			else if(value instanceof Number)
			{
				json.add(JSONUtil.newGsonPrimitive(value));
			}
			else if(value instanceof Boolean)
			{
				json.add(JSONUtil.newGsonPrimitive(value));
			}
		}
		return json;
	}

	@Override
	public void fromGson(JsonElement init) 
	{
		JsonArray list = init.getAsJsonArray();
		for(JsonElement element : list)
		{
			if(element.isJsonObject())
			{
				JSONObject json = new JSONObject();
				json.fromGson(element);
				this.add(json);
			}
			else if(element.isJsonArray())
			{
				JSONArray array = new JSONArray();
				array.fromGson(element);
				this.add(array);
			}
			else if(element.isJsonNull())
			{
				this.add(null);
			}
			else if(element.isJsonPrimitive())
			{
				JsonPrimitive primitive = (JsonPrimitive)element;
				if(primitive.isBoolean())
				{
					this.add(primitive.getAsBoolean());
				}
				else if(primitive.isNumber())
				{
					this.add(primitive.getAsNumber());
				}
				else if(primitive.isString())
				{
					this.add(primitive.getAsString());
				}
			}
		}
	}
}
