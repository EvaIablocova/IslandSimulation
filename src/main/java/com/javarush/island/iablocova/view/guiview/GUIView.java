package com.javarush.island.iablocova.view.guiview;

import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.view.View;
import com.javarush.island.iablocova.view.guiview.util.CreatureViewer;
import com.javarush.island.iablocova.view.guiview.util.GUIExchanger;
import com.javarush.island.iablocova.view.guiview.util.GUIStarter;
import com.javarush.island.iablocova.view.guiview.util.GUISynchronizer;
import javafx.application.Platform;

import java.util.concurrent.BrokenBarrierException;

public class GUIView implements View
{
    private CustomData customData;

    @Override
    public CustomData getCustomerParameters() throws InterruptedException
    {
        GUIStarter guiStarter = new GUIStarter();
        Thread guiStarterThread = new Thread(guiStarter, "GUIStarter");
        guiStarterThread.start();
        customData = GUIExchanger.customDataExchanger.exchange(customData);
        return customData;
    }

    @Override
    public void printStatistics(Statistics statistics) throws InterruptedException
    {
        GUIExchanger.statisticsExchanger.exchange(statistics);
    }

    @Override
    public void setIsland(Island island) throws InterruptedException
    {
        GUIExchanger.islandExchanger.exchange(island);
    }

    @Override
    public void synchronize() throws BrokenBarrierException, InterruptedException
    {
        GUISynchronizer.BARRIER.await();
        Thread.sleep(2000);
    }

    @Override
    public void movePicture(Creature thisCreature, Cell oldCell)
    {
        Cell newCell = thisCreature.getCurrentCell();
        Cell cell1;
        Cell cell2;
        if (oldCell.hashCode() < newCell.hashCode())
        {
            cell1 = oldCell;
            cell2 = newCell;
        }
        else
        {
            cell1 = newCell;
            cell2 = oldCell;
        }
        synchronized(cell1)
        {
            synchronized (cell2)
            {
                Platform.runLater(() -> CreatureViewer.moveCreaturePicture(thisCreature, oldCell));
            }
        }
    }

    @Override
    public void deletePicture(Creature creature)
    {
        synchronized (creature.getCurrentCell())
        {
            Platform.runLater(() -> CreatureViewer.deleteCreaturePicture(creature));
        }
    }

    @Override
    public void showPicture(Creature creature)
    {
        synchronized (creature.getCurrentCell())
        {
            Platform.runLater(() -> CreatureViewer.showCreaturePicture(creature));
        }
    }
}
