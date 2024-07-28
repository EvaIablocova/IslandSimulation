package com.javarush.island.iablocova.util;

import java.util.ArrayList;
import java.util.Collections;

import com.javarush.island.iablocova.entity.Coordinates;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;
import com.javarush.island.iablocova.view.View;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeadsCollector implements Runnable
{
    private final Island island;

    public DeadsCollector(Island island)
    {
        this.island = island;
    }

    @Override
    public void run()
    {
        Cell[][] cells = island.getCells();
        ArrayList<Coordinates> coordinates = new ArrayList<>();

        for (int y = 0; y < island.getCustomData().getHeight(); y++)
        {
            for (int x = 0; x < island.getCustomData().getWidth(); x++)
            {
                coordinates.add(new Coordinates(x, y));
            }
        }

        Collections.shuffle(coordinates);

        for(Coordinates coordinate : coordinates)
        {
            synchronized (cells[coordinate.getY()][coordinate.getX()])
            {
                for(Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
                {
                    ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures = cells[coordinate.getY()][coordinate.getX()].getCellCreatures();
                    Set<Creature> cellCreaturesOfSpecies = cellCreatures.get(clazz);
                    Iterator<Creature> it = cellCreaturesOfSpecies.iterator();
                    while(it.hasNext())
                    {
                        Creature creature = it.next();
                        View view;
                        if (creature == null)
                        {
                            it.remove();
                        }
                        else
                        {
                            view = creature.getView();
                            if (creature instanceof Animal && (!creature.isAlive() || creature.getAge() >= creature.getMaxAge()))
                            {
                                view.deletePicture(creature);
                                it.remove();
                            }
                            else if (creature instanceof Plant && (!creature.isAlive() || creature.getWeight() == 0))
                            {
                                view.deletePicture(creature);
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
    }
}
