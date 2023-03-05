package jml.evilnotch.lib.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.apache.commons.lang3.ClassUtils;

import jml.evilnotch.lib.JavaUtil;
import jml.evilnotch.lib.Validate;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReflectionHandler {
	
	public static Field modifiersField;
	static
	{
		try
		{
			modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
	
    public static Field getField(Class clazz, MCPSidedString mcp)
    {
    	return getField(clazz, mcp.toString());
    }
	
	/**
	 * makes the field public and strips the final modifier
	 */
    public static Field getField(Class clazz, String name)
    {
        try
        {
        	return grabField(clazz, name);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        return null;
    }
    
    private static Field grabField(Class clazz, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException 
    {
    	Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		return field;
	}
    
    /**
     * use if you have multiple possible fields doesn't output errors
     */
    public static Field findField(Class clazz, String... names)
    {
    	for(String name : names)
    	{
    		try
    		{
    			return grabField(clazz, name);
    		}
    		catch(Throwable t)
    		{
    			
    		}
    	}
    	return null;
    }

	public static Method getMethod(Class clazz, MCPSidedString mcp)
    {
    	return getMethod(clazz, mcp.toString());
    }
    
    public static Method getMethod(Class clazz, String name, Class... params)
    {
        try
        {
            Method method = clazz.getDeclaredMethod(name, params);
            method.setAccessible(true);
            return method;
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        return null;
    }
    
    public static Object getStatic(Field field)
    {
    	return get(field, null);
    }
    
    public static Object get(Field field, Object instance)
    {
    	try 
    	{
			return field.get(instance);
		} 
    	catch (Throwable t)
    	{
			t.printStackTrace();
		} 
    	return null;
    }
    
    public static void setStatic(Field field, Object toset)
    {
    	set(field, null, toset);
    }
    
    public static void set(Field field, Object instance, Object toSet)
    {
    	try 
    	{
			field.set(instance, toSet);
		} 
    	catch (Throwable t)
    	{
			t.printStackTrace();
		} 
    }
    
    public static Object invokeStatic(Method method, Object... params)
    {
    	return invoke(method, null, params);
    }
    
    public static Object invoke(Method method, Object instance, Object... params)
    {
    	try
    	{
    		return method.invoke(instance, params);
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    }

    //fields
	public static Boolean getBoolean(Field field, Object instance) 
	{
		return (Boolean) get(field, instance);
	}
	
	public static Byte getByte(Field field, Object instance)
	{
		return (Byte) get(field, instance);
	}
	
	public static Short getShort(Field field, Object instance)
	{
		return (Short) get(field, instance);
	}
	
	public static Integer getInt(Field field, Object instance)
	{
		return (Integer) get(field, instance);
	}
	
	public static Long getLong(Field field, Object instance)
	{
		return (Long) get(field, instance);
	}
	
	public static Float getFloat(Field field, Object instance)
	{
		return (Float) get(field, instance);
	}
	
	public static Double getDouble(Field field, Object instance)
	{
		return (Double) get(field, instance);
	}
	
	//static fields
	public static Boolean getStaticBoolean(Field field) 
	{
		return (Boolean) getStatic(field);
	}
	
	public static Byte getStaticByte(Field field)
	{
		return (Byte) getStatic(field);
	}
	
	public static Short getStaticShort(Field field)
	{
		return (Short) getStatic(field);
	}
	
	public static Integer getStaticInt(Field field)
	{
		return (Integer) getStatic(field);
	}
	
	public static Long getStaticLong(Field field)
	{
		return (Long) getStatic(field);
	}
	
	public static Float getStaticFloat(Field field)
	{
		return (Float) getStatic(field);
	}
	
	public static Double getStaticDouble(Field field)
	{
		return (Double) getStatic(field);
	}
	
	//method
    public static Boolean invokeBoolean(Method method, Object instance, Object... params)
    {
    	return (Boolean) invoke(method, instance, params);
    }
    
    public static Byte invokeByte(Method method, Object instance, Object... params)
    {
    	return (Byte) invoke(method, instance, params);
    }
    
    public static Short invokeShort(Method method, Object instance, Object... params)
    {
    	return (Short) invoke(method, instance, params);
    }
    
    public static Integer invokeInt(Method method, Object instance, Object... params)
    {
    	return (Integer) invoke(method, instance, params);
    }
    
    public static Long invokeLong(Method method, Object instance, Object... params)
    {
    	return (Long) invoke(method, instance, params);
    }
    
    public static Float invokeFloat(Method method, Object instance, Object... params)
    {
    	return (Float) invoke(method, instance, params);
    }
    
    public static Double invokeDouble(Method method, Object instance, Object... params)
    {
    	return (Double) invoke(method, instance, params);
    }
    
    //method static
    public static Boolean invokeStaticBoolean(Method method, Object... params)
    {
    	return (Boolean) invokeStatic(method, params);
    }
    
    public static Byte invokeStaticByte(Method method, Object... params)
    {
    	return (Byte) invokeStatic(method, params);
    }
    
    public static Short invokeStaticShort(Method method, Object... params)
    {
    	return (Short) invokeStatic(method, params);
    }
    
    public static Integer invokeStaticInt(Method method, Object... params)
    {
    	return (Integer) invokeStatic(method, params);
    }
    
    public static Long invokeStaticLong(Method method, Object... params)
    {
    	return (Long) invokeStatic(method, params);
    }
    
    public static Float invokeStaticFloat(Method method, Object... params)
    {
    	return (Float) invokeStatic(method, params);
    }
    
    public static Double invokeStaticDouble(Method method, Object... params)
    {
    	return (Double) invokeStatic(method, params);
    }
    
    public static Constructor getConstructor(Class clazz, Class... params)
    {
    	try
    	{
    		Constructor ctr =  clazz.getDeclaredConstructor(params);
    		ctr.setAccessible(true);
    		return ctr;
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
		return null;
    }
    
    /**
     * convert all primitive classes I check into their wrapper classes
     */
    public static Constructor getWrappedConstructor(Class clazz, Class... params)
    {
    	try
    	{
    		JavaUtil.getWrappedClasses(params);
    		Constructor[] ctrs = clazz.getDeclaredConstructors();
    		for(Constructor ctr : ctrs)
    		{
    			Class[] compare = JavaUtil.getWrappedClasses(ctr.getParameterTypes());
    			if(JavaUtil.equals(compare, params))
    				return ctr;
    		}
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    }
    
    public static Class getClass(String className)
    {
    	try
    	{
    		return Class.forName(className);
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    }
    
    
    public static Class getClass(String name, boolean clinit, ClassLoader loader)
    {
    	try
    	{
    		return Class.forName(name, clinit, loader);
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    } 
    
    public static Class getArrayClass(Object[] arr)
    {
    	return getArrayClass(arr.getClass());
    }
    
    public static Class getArrayClass(Class clazz)
    {
    	Validate.isTrue(clazz.isArray());
    	return clazz.getComponentType();
    }
    
    public static Object cast(Class clazz, Object tocast)
    {
    	return clazz.cast(tocast);
    }
    
    public static ClassLoader getClassLoader(Class clazz)
    {
    	return clazz.getClassLoader();
    }
    
    public static boolean instanceOf(Class base, Class compare)
    {
    	return base.isAssignableFrom(compare);
    }
    
    public static boolean instanceOf(Class base, Object obj)
    {
    	return base.isInstance(obj);
    }
    
    public static Object newInstance(Class clazz)
    {
    	try
    	{
    		return clazz.newInstance();
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    }
    
    public static Annotation getClassAnnotation(Class clazz, Class<? extends Annotation> annot)
    {
    	try
    	{
    		return clazz.getDeclaredAnnotation(annot);
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return null;
    }
    
    public static Class getAnnotationClass(Annotation an)
    {
    	return an.annotationType();
    }
    
    public static boolean containsInterface(Class clazz, Class intf)
    {
    	try
    	{
    		Validate.isTrue(intf.isInterface());
    		if(clazz.isInterface())
    			return instanceOf(intf, clazz);
    		for(Class c : ClassUtils.getAllInterfaces(clazz))
    		{
    			if(instanceOf(intf, c))
    				return true;
    		}
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    	return false;
    }
    
    /**
     * like forges but, can be applied to any enum
     */
    public static Enum addEnum(Class<? extends Enum> clazz, String name, Object... params)
    {
    	Enum e = ReflectEnum.createEnum(clazz, name, params);
    	ReflectEnum.addEnum(e);
    	return e;
    }
    
    /**
     * create an enum without instantiating it into class enum arrays
     */
    public static Enum newEnum(Class<? extends Enum> clazz, String name, Object... params)
    {
    	Enum e = ReflectEnum.createEnum(clazz, name, params);
    	return e;
    }
    
    public static void addEnum(Enum... enums)
    {
    	ReflectEnum.addEnum(enums);
    }
    
    public static boolean containsEnum(Class<? extends Enum> clazz, String name)
    {
    	return ReflectEnum.containsEnum(clazz, name);
    }
    
    public static Enum getEnum(Class<? extends Enum> clazz, String name)
    {
    	return ReflectEnum.getEnum(clazz, name);
    }
    
    public static <T> T[] newArray(Class<T> clazz, int size)
    {
    	return (T[]) Array.newInstance(clazz, size);
    }
    
	/**
     * if your java security for some reason is high call this method each time before using a field in reflection
     * REPORT IT AS A BUG TO ME IF YOU EVER NEED TO USE THIS!
     */
    public static void makeAccessible(Field field) throws Exception 
    {
        field.setAccessible(true);
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
}
