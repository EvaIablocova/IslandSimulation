package com.javarush.island.iablocova.app;

import com.javarush.island.iablocova.controller.Controller;
import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.exception.CreateObjectException;
import com.javarush.island.iablocova.util.IslandScheduler;
import com.javarush.island.iablocova.util.Spawner;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Exchanger;

@RequiredArgsConstructor
public class Application {
    private final Controller controller;
    private Island island;
    private Statistics statistics;
    private Exchanger<Statistics> exchanger;

    public void run()
    {
        try
        {
            CustomData customData = controller.getView().getCustomerParameters();
            island = new Island(customData);
            spawnIslandCreatures();
            controller.getView().setIsland(island);
            controller.getView().synchronize();
            startLivingProcess();
        }
        catch (CreateObjectException | IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (BrokenBarrierException e)
        {
            e.printStackTrace();
        }
    }

    private void spawnIslandCreatures() throws CreateObjectException, IOException
    {
        Spawner spawner = new Spawner(island, controller.getView());
        spawner.spawnIslandCreatures();
    }

    public void startLivingProcess() throws CreateObjectException, IOException, InterruptedException
    {
        exchanger = new Exchanger<>();
        IslandScheduler islandScheduler = new IslandScheduler(island, exchanger);

        islandScheduler.startActivity();
    }

    public Statistics getStatistics()
    {
        try
        {
            statistics = exchanger.exchange(statistics);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return statistics;
    }

    public void printStatistics(Statistics statistics) throws InterruptedException
    {
        controller.getView().printStatistics(statistics);
    }


}
