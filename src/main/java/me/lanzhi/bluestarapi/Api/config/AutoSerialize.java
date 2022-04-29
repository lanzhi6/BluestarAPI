package me.lanzhi.bluestarapi.Api.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AutoSerialize
{
    private final static HashMap<String,Class<? extends AutoSerializeInterface>> serializableClazz=new HashMap<>();

    public static AutoSerializeInterface deserialize(Map<String, Object> map)
    {
        String clazzName=(String) map.get("class");
        System.out.println("反序列化: "+clazzName);
        Class<? extends AutoSerializeInterface> clazz;
        clazz=serializableClazz.get(clazzName);
        if (clazz==null)
        {
            Bukkit.getLogger().warning(ChatColor.RED+"反序列化失败,未找到类: \""+clazzName+"\"");
        }
        AutoSerializeInterface object;
        try
        {
            object=clazz.newInstance();
        }
        catch (Throwable e)
        {
            System.out.println(ChatColor.RED+"新建对象失败");
            throw new RuntimeException(e);
        }
        Field[] fields=clazz.getDeclaredFields();
        for (Field field: fields)
        {
            if ((field.getModifiers()&8)!=0)
            {
                continue;
            }
            field.setAccessible(true);
            try
            {
                field.set(object,map.get(field.getName()));
            }
            catch (IllegalAccessException e)
            {
                System.out.println(ChatColor.RED+"设置变量失败");
                throw new RuntimeException(e);
            }
        }
        return object;
    }
    public static void registerClass(Class<? extends AutoSerializeInterface> clazz)
    {
        registerClass(clazz,clazz.getName());
    }
    public static void registerClass(Class<? extends AutoSerializeInterface> clazz,String name)
    {
        serializableClazz.put(name,clazz);
    }
}
