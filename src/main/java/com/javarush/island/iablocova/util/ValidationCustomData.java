package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.config.IslandConfig;

public class ValidationCustomData
{
    private ValidationCustomData()
    {
    }

    public static int validateWidth(String width)
    {
        if (width.equals(""))
            return IslandConfig.width;

        int n;
        try
        {
            n = Integer.parseInt(width);
            if (n >= IslandConfig.MIN_WIDTH && n <= IslandConfig.MAX_WIDTH)
                return n;
            else
                return -1;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static int validateHeight(String height)
    {
        if (height.equals(""))
            return IslandConfig.height;

        int n;
        try
        {
            n = Integer.parseInt(height);
            if (n >= IslandConfig.MIN_HEIGHT && n <= IslandConfig.MAX_HEIGHT)
                return n;
            else
                return -1;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static long validateDayLength(String dayLength)
    {
        if (dayLength.equals(""))
            return IslandConfig.dayLength / 1000;

        long n;
        try
        {
            n = Long.parseLong(dayLength);
            if (n >= IslandConfig.MIN_DAY_LENGTH / 1000 && n <= IslandConfig.MAX_DAY_LENGTH / 1000)
                return n;
            else
                return -1;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }
}

