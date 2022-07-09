package me.lanzhi.bluestarapi.api;

import org.bukkit.ChatColor;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RGBColor
{
    private final static int MASK=0xff;
    private static final Pattern hex=Pattern.compile("#([0-9A-Fa-fK-Ok-oRr]{6})");
    private final int r;
    private final int g;
    private final int b;
    private final int format;

    public RGBColor(int color)
    {
        if (color<0)
        {
            color=0;
        }
        this.r=getRed(color)&MASK;
        this.g=getGreen(color)&MASK;
        this.b=getBlue(color)&MASK;
        format=-1;
    }

    public RGBColor(int r,int g,int b)
    {
        this.r=r&MASK;
        this.g=g&MASK;
        this.b=b&MASK;
        format=-1;
    }

    public static int getRed(int color)
    {
        return (color >> 16)&MASK;
    }

    public static int getGreen(int color)
    {
        return (color >> 8)&MASK;
    }

    public static int getBlue(int color)
    {
        return color&MASK;
    }

    public static String getHexRed(int color)
    {
        return Integer.toHexString(getRed(color));
    }

    public static String getHexGreen(int color)
    {
        return Integer.toHexString(getGreen(color));
    }

    public static String getHexBlue(int color)
    {
        return Integer.toHexString(getBlue(color));
    }

    public static int getColor(int r,int g,int b)
    {
        return (r<<16)+(g<<8)+b;
    }

    public static String getHexColor(int r,int g,int b)
    {
        return Integer.toHexString(getColor(r,g,b));
    }

    public static String getHexColor(int color)
    {
        return getHexRed(color)+getHexGreen(color)+getHexBlue(color);
    }

    public static String toColorCode(String hexColor)
    {
        StringBuilder builder=new StringBuilder("§x");
        for (char i: hexColor.toCharArray())
        {
            if ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(i)<=-1)
            {
                continue;
            }
            builder.append("§").append(i);
        }
        return builder.toString();
    }

    public static String setColor(String message)
    {
        StringBuilder stringBuilder=new StringBuilder();
        Matcher matcher=hex.matcher(message);
        while (matcher.find())
        {
            matcher.appendReplacement(stringBuilder,toColorCode(matcher.group()));
        }
        matcher.appendTail(stringBuilder);
        return ChatColor.translateAlternateColorCodes('&',stringBuilder.toString());
    }

    public static RGBColor random()
    {
        return new RGBColor(new Random().nextInt(0xffffff));
    }

    public int getRed()
    {
        return r;
    }

    public int getGreen()
    {
        return g;
    }

    public int getBlue()
    {
        return b;
    }

    public String getHexRed()
    {
        return Integer.toHexString(r);
    }

    public String getHexGreen()
    {
        return Integer.toHexString(g);
    }

    public String getHexBlue()
    {
        return Integer.toHexString(b);
    }

    public int getColor()
    {
        return getColor(r,g,b);
    }

    public String getHexColor()
    {
        return getHexColor(r,g,b);
    }

    public String toColorCode()
    {
        return toColorCode(getHexColor());
    }

    public boolean isFormat()
    {
        return format!=-1;
    }

    public boolean isColor()
    {
        return !isFormat();
    }

    @Override
    public String toString()
    {
        return toColorCode();
    }
}
