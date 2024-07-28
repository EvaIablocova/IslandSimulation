package com.javarush.island.iablocova.view.guiview.util;

import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.view.consoleview.ConsoleView;
import com.javarush.island.iablocova.view.guiview.IslandMainApplication;

public class StatisticsUpdater implements Runnable
{
    private final IslandMainApplication islandMainApplication;
    private Statistics statistics;

    public StatisticsUpdater(IslandMainApplication islandMainApplication)
    {
        this.islandMainApplication = islandMainApplication;
        this.statistics = islandMainApplication.getStatistics();
    }

    @Override
    public void run()
    {
        try
        {
            statistics = GUIExchanger.statisticsExchanger.exchange(statistics);
            islandMainApplication.setStatistics(statistics);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        ConsoleView consoleView = new ConsoleView();
        consoleView.printStatistics(statistics);
    }
}
