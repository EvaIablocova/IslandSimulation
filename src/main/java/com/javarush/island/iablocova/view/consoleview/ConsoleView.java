package com.javarush.island.iablocova.view.consoleview;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.constants.Conversation;
import com.javarush.island.iablocova.constants.Text;
import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.util.ValidationCustomData;
import com.javarush.island.iablocova.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;

public class ConsoleView implements View {
    private CustomData customData;

    @Override
    public CustomData getCustomerParameters()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            int width;
            int height;
            long dayLength;

            System.out.println(Conversation.GREETINGS);
            width = getWidth(reader);
            height = getHeight(reader);
            dayLength = getDayLength(reader);
            customData = new CustomData(width, height, dayLength);
            IslandConfig.width = width;
            IslandConfig.height = height;
            IslandConfig.dayLength = dayLength;
            System.out.println(Conversation.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return customData;
    }

    private int getWidth(BufferedReader reader) throws IOException
    {
        System.out.println(Conversation.CONVERSATION_ABOUT_WIDTH);
        String widthStr;
        int width;
        while (true)
        {
            widthStr = reader.readLine();
            width = ValidationCustomData.validateWidth(widthStr);
            if (width >= 0)
                break;
            System.out.println(Conversation.INCORRECT_WIDTH);
        }
        return width;
    }

    private int getHeight(BufferedReader reader) throws IOException
    {
        System.out.println(Conversation.CONVERSATION_ABOUT_HEIGHT);
        String heightStr;
        int height;
        while (true)
        {
            heightStr = reader.readLine();
            height = ValidationCustomData.validateHeight(heightStr);
            if (height >= 0)
                break;
            System.out.println(Conversation.INCORRECT_HEIGHT);
        }
        return height;
    }

    private long getDayLength(BufferedReader reader) throws IOException
    {
        System.out.println(Conversation.CONVERSATION_ABOUT_DAY_LENGTH);
        String dayLengthStr;
        long dayLength;
        while (true)
        {
            dayLengthStr = reader.readLine().replace(',', '.');
            dayLength = ValidationCustomData.validateDayLength(dayLengthStr);
            if (dayLength >= 0)
                break;
            System.out.println(Conversation.INCORRECT_DAY_LENGTH);
        }
        return dayLength * 1000;
    }

    @Override
    public void printStatistics(Statistics statistics)
    {
        if (statistics.getPopulation() > 0)
        {
            System.out.println(Text.PERECHEN_IN_THE_END_OF_DAY);
            for (Class<? extends Creature> clazz : statistics.getAllCreaturesPopulation().keySet())
            {
                System.out.printf(Text.CREATURE_LINE, clazz.getSimpleName(), statistics.getAllCreaturesPopulation().get(clazz));
            }
            System.out.printf(Text.POPULATION, statistics.getPopulation());
        }
        else
        {
            System.out.println(Text.ALL_ANIMALS_ARE_INSTINCT);
        }
        System.out.print(statistics.getResult().getDayLoadTime());
        System.out.print(statistics.getResult().getStatisticsTime());
        System.out.print(statistics.getResult().getDayLength());
    }

    @Override
    public void setIsland(Island island)
    {
        // Метод-заглушка. Данный метод нужен для GUIView.
    }

    @Override
    public void synchronize()
    {
        // Метод-заглушка. Данный метод нужен для GUIView.
    }

    @Override
    public void movePicture(Creature thisCreature, Cell oldCell)
    {
        // Метод-заглушка. Данный метод нужен для GUIView.
    }

    @Override
    public void deletePicture(Creature creature)
    {
        // Метод-заглушка. Данный метод нужен для GUIView.
    }

    @Override
    public void showPicture(Creature creature)
    {
        // Метод-заглушка. Данный метод нужен для GUIView.
    }
}
