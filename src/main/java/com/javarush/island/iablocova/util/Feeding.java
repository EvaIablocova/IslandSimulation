package com.javarush.island.iablocova.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Animal;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.view.View;


public class Feeding
{
    private static Cell currentCell;
    private static ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures;

    private Feeding()
    {
    }

    public static boolean foodIsAvailable(Creature thisCreature)
    {
        currentCell = thisCreature.getCurrentCell();
        cellCreatures = currentCell.getCellCreatures();
        boolean foodIsAvailable = false;
        HashMap<Class<? extends Creature>, Integer> foodMap = thisCreature.getFoodMap();

        for(Class<? extends Creature> clazz : foodMap.keySet())
        {
            if (cellCreatures.get(clazz).size() > 0)													// Если на данной локации множество хотя бы одного из съедобных видов не пустое, значит еда есть
                foodIsAvailable = true;
        }
        return foodIsAvailable;																							// Если не нашлось ни одного существа любого вида, который является съедобным для данной особи, еды в данной локации для особи нет
    }

    public static void eat(Creature thisCreature) throws InterruptedException
    {
        currentCell = thisCreature.getCurrentCell();
        cellCreatures = currentCell.getCellCreatures();
        HashMap<Class<? extends Creature>, Integer> foodMap = thisCreature.getFoodMap();
        Set<Class<? extends Creature>> eatableCreatures = foodMap.keySet();												// Список типов, которые можно съесть
        ArrayList<Creature> foodList = new ArrayList<>();																// Инициализация листа созданий, которые могут быть съедены

        for(Class<? extends Creature> clazz : eatableCreatures)
        {
            foodList.addAll(cellCreatures.get(clazz));
        }
        int i = GetRandom.RANDOM.nextInt(foodList.size());

        Creature victim = foodList.get(i);
        try
        {
            if (cellCreatures.get(victim.getClass()).contains(victim)
                    && victim.getLock().tryLock(IslandConfig.dayLength / 500, TimeUnit.MILLISECONDS))			// Если жертва не убежала в другую локацию и если лок жертвы удалось получить
            {
                if (GetRandom.RANDOM.nextInt(100) < foodMap.get(victim.getClass()))								// то тогда пытаемся поймать жертву. При положительном условии поймать жертву удалось и мы едим её.
                {
                    if (victim instanceof Plant)
                    {
                        if (thisCreature.getWantToEat() >= victim.getWeight())
                        {
                            ((Plant) victim).isAlive = false;
                            thisCreature.setWantToEat(thisCreature.getWantToEat() - victim.getWeight());
                            victim.setWeight(0);
                        }
                        else if (thisCreature.getWantToEat() <= victim.getWeight())
                        {
                            victim.setWeight(victim.getWeight() - thisCreature.getWantToEat());
                            thisCreature.setWantToEat(0);
                        }
                        System.out.printf("Животное %1$s поело растение %2$s.%n", thisCreature.getClass().getSimpleName() + thisCreature.hashCode(), victim.getClass().getSimpleName() + victim.hashCode());
                    }
                    else if (victim instanceof Animal)
                    {
                        victim.setAlive(false);
                        thisCreature.setWantToEat(thisCreature.getWantToEat() > victim.getWeight() ? thisCreature.getWantToEat() - victim.getWeight() : 0);
                        System.out.printf("Животное %1$s съело животное %2$s.%n", thisCreature.getClass().getSimpleName() + thisCreature.hashCode(), victim.getClass().getSimpleName() + victim.hashCode());
                    }
                }
            }
        }
        finally
        {
            if (!victim.isAlive())																						// Если жертва мертва, то удаляем жертву из списков живых существ локации
            {
                View view = victim.getView();
                view.deletePicture(victim);
                cellCreatures.get(victim.getClass()).remove(victim);
            }
            victim.getLock().unlock();
        }

    }
}
