package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;

public class MonitoringStatistics implements Callable<Statistics>, Timeable
{
    private final Island island;
    public MonitoringStatistics(Island island)
    {
        this.island = island;
    }

    @Override
    public Statistics call()
    {
        Instant start = Instant.now();
        Cell[][] cells = island.getCells();
        Statistics statistics = new Statistics();
        Set<Creature> creaturesOfCertainSpeciesInCell;
        synchronized (island)
        {
            for (Cell[] lineOfCells : cells)
            {
                for (Cell value : lineOfCells)
                {
                    for (Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
                    {
                        creaturesOfCertainSpeciesInCell = value.getCellCreatures().get(clazz);
                        HashMap<Class<? extends Creature>, Integer> allCreaturesPopulation = statistics.getAllCreaturesPopulation();
                        int quantity = allCreaturesPopulation.get(clazz);
                        allCreaturesPopulation.put(clazz, quantity + creaturesOfCertainSpeciesInCell.size());
                        if (clazz != Plant.class)
                            statistics.setPopulation(statistics.getPopulation() + creaturesOfCertainSpeciesInCell.size());
                    }
                }
            }
        }
        Instant stop = Instant.now();
        statistics.setTime(getMillis(start, stop));
        return statistics;
    }
}
