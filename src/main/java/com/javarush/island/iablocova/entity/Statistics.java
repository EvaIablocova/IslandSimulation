package com.javarush.island.iablocova.entity;

import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;

import java.util.HashMap;

public class Statistics {
    private final HashMap<Class<? extends Creature>, Integer> allCreaturesPopulation;
    private long time;
    private Result result;
    private int population;

    public Statistics()
    {
        allCreaturesPopulation = new HashMap<>();
        population = 0;
        for(Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
            allCreaturesPopulation.put(clazz, 0);
    }

    public HashMap<Class<? extends Creature>, Integer> getAllCreaturesPopulation()
    {
        return allCreaturesPopulation;
    }
    public long getTime()
    {
        return time;
    }
    public void setTime(long time)
    {
        this.time = time;
    }
    public Result getResult()
    {
        return result;
    }

    public void setResult(Result result)
    {
        this.result = result;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }
}
