package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.view.View;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.exception.CreateObjectException;
import com.javarush.island.iablocova.factory.CreaturesFactory;
import com.javarush.island.iablocova.repository.CreaturePopulation;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;

import java.io.IOException;

public class Spawner
{
    private final Cell[][] cells;
    private final int width;
    private final int height;
    private final View view;

    public Spawner(Island island, View view)
    {
        cells = island.getCells();
        this.view = view;
        this.width = island.getCustomData().getWidth();
        this.height = island.getCustomData().getHeight();
    }

    public void spawnIslandCreatures() throws CreateObjectException, IOException
    {
        for(Class<? extends Creature> creatureClass: SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
        {
            int x = GetRandom.RANDOM.nextInt(width);
            int y = GetRandom.RANDOM.nextInt(height);
            String className = creatureClass.getSimpleName();

            if ( !CreaturePopulation.population.containsKey(className) )
                throw new CreateObjectException(String.format("Класс %s отсутствует в списке популяций животных и растений острова!", className));
            else if ( !creatureClass.isAnnotationPresent(Config.class))
                throw new CreateObjectException(String.format("Класс %s должен иметь аннотацию Config!", className));

            int population = CreaturePopulation.population.get(creatureClass.getSimpleName());
            for (int i = 0; i < population; i++)
            {
                Creature creature = CreaturesFactory.getInstance(creatureClass, cells[y][x], view);

                int randomX = GetRandom.RANDOM.nextInt(width);
                int randomY = GetRandom.RANDOM.nextInt(height);
                cells[randomY][randomX].getCellCreatures().get(creatureClass).add(creature);
                creature.setCurrentCell(cells[randomY][randomX]);


            }
        }
    }
}

