package jml.evilnotch.lib.json.serialize;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Method;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import jml.evilnotch.lib.json.JSONArray;
import jml.evilnotch.lib.json.JSONObject;
import jml.evilnotch.lib.json.JSONUtil;
import jml.evilnotch.lib.reflect.ReflectionHandler;

public class JSONSerializer {
	
	public static final Gson pGson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
	public static final Gson normalGson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
	
	public boolean isPretty;
	public JSONSerializer()
	{
		
	}
	
	public void setPrettyPrint(boolean enabled)
	{
		this.isPretty = enabled;
	}
	
	public JSONArray readJSONArray(String str)
	{
		return (JSONArray) read(new StringReader(str));
	}
	
	public JSONObject readJSONObject(String str)
	{
		return (JSONObject) read(new StringReader(str));
	}
	
	public JSONArray readJSONArray(Reader reader)
	{
		return (JSONArray) read(reader);
	}
	
	public JSONObject readJSONObject(Reader reader)
	{
		return (JSONObject) read(reader);
	}
	
	public IGsonable read(Reader reader)
	{
		try
		{
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new JsonReader(reader));
			IGsonable json = element instanceof JsonObject ? new JSONObject() : new JSONArray();
			json.fromGson(element);
			return json;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void write(IGsonable json, Writer writer) throws IOException
	{
		try
		{
			Gson gson = this.getGson();
			gson.toJson(json.toGson(), newJsonWriter(gson, writer));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Method newJson = ReflectionHandler.getMethod(Gson.class, "newJsonWriter", Writer.class);
	public JsonWriter newJsonWriter(Gson gson, Writer writer)
	{
		return (JsonWriter) ReflectionHandler.invoke(newJson, gson, writer);
	}
	
	public String toJSONString(IGsonable json) 
	{
		Gson gson = normalGson;
		String text = gson.toJson(json.toGson());
		return text;
	}

	/**
	 * do not use for saving files use write instead
	 */
	public String toPrettyPrint(IGsonable json)
	{
		Gson gson = pGson;
		String text = gson.toJson(json.toGson());
	    text = text.replaceAll("\n", "\r\n");
	    return text;
	}
	
	public Gson getGson()
	{
		return this.isPretty ? pGson : normalGson;
	}

}
