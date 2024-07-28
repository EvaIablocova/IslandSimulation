package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CreatureParametersManager implements Runnable
{
    private final Island island;

    public CreatureParametersManager(Island island)
    {
        this.island = island;
    }

    @Override
    public void run()
    {
        synchronized (island)
        {
            Cell[][] cells = island.getCells();
            for (int y = 0; y < island.getCustomData().getHeight(); y++)
            {
                for (int x = 0; x < island.getCustomData().getWidth(); x++)
                {
                    for(Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)
                    {
                        ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures = cells[y][x].getCellCreatures();
                        Set<Creature> cellCreaturesOfSpecies = cellCreatures.get(clazz);
                        for (Creature creature : cellCreaturesOfSpecies)
                        {
                            if (creature instanceof Animal)
                            {
                                creature.setAge(creature.getAge() + 1);													// Животное стало ещё на один день старше.

                                double d = creature.getWantToEat() - creature.getMassOfFood() * 0.1;					// Животное хочет есть на 10 % больше за каждый день.
                                double wantToEat = Math.min(d, creature.getMassOfFood());								// Но не больше максимального объёма еды, которое может съесть.
                                creature.setWantToEat(wantToEat);

                                if (creature.getWantToEat() > creature.getMassOfFood() * 0.8)							// Если животное голодно на 80 %,
                                {
                                    creature.setWeight(creature.getWeight() * 0.9);										// Тогда оно теряет 10 % массы.
                                }

                                if (creature.getWeight() < creature.getMaxWeight() * 0.5)								// Если животное похудело больше чем на 50 %, тогда оно умирает от голода
                                    creature.setAlive(false);
                            }
                            else if (creature instanceof Plant)															// У растений просто удваивается вес до значения 1024
                            {
                                if (creature.getWeight() < 1024)
                                    creature.setWeight(creature.getWeight() * 2);
                            }
                        }
                    }
                }
            }
        }
    }
}
