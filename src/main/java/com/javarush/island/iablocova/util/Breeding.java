package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.config.IslandConfig;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.factory.CreaturesFactory;
import com.javarush.island.iablocova.view.View;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Breeding {
    private static Cell currentCell;

    private Breeding()
    {
    }

    public static Creature availablePair(Creature thisCreature) throws InterruptedException
    {
        currentCell = thisCreature.getCurrentCell();
        Set<Creature> relatives = currentCell.getCellCreatures().get(thisCreature.getClass());						// Получаем ссылку на множество всех сородичей в текущей локации

        for (Creature pair : relatives)
        {
            if (pair == thisCreature)																					// С собой размножиться не получится
                continue;
            if( pair.getLock().tryLock(IslandConfig.dayLength / 500, TimeUnit.MILLISECONDS) )					// Если получилось занять животное pair, проверяем, подходит ли оно
            {
                if (pair.isMale() != thisCreature.isMale() && !pair.isBred() && pair.getAge() > pair.getMatureAge())	// Проверка, подходящая ли особь для размножения. Если да, тогда пытаемся её занять и возвращаем его
                    return pair;
                else																									// Если нет, тогда отпускаем
                    if (pair.getLock().isHeldByCurrentThread())
                        pair.getLock().unlock();
            }
        }
        return null;																									// Если подходящего животного не нашлось, тогда возвращаем null
    }

    public static boolean breed(Creature thisCreature, Creature pair)
    {
        if (pair == null)																								// Проверка на то, нашлась ли пара для размножения
            return false;																								// Если не нашлась, то возвращаем false

        Creature newCreature = null;
        try																												// Если нашлась, то размножаемся с ней
        {
            currentCell = thisCreature.getCurrentCell();
            newCreature = CreaturesFactory.clone(thisCreature);
            newCreature.getLock().lock();
            newCreature.setAge(0);
            newCreature.setWantToEat(0);
            pair.setBred(true);
            thisCreature.setBred(true);
            currentCell.getCellCreatures().get(newCreature.getClass()).add(newCreature);								// TODO синхронизовать список животных для записи
            View view = newCreature.getView();
            view.showPicture(newCreature);
            System.out.printf("Животное %1$s размножилось с животным %2$s. В результате размножения появилось животное %3$s.%n", thisCreature.getClass().getSimpleName() + thisCreature.hashCode(), pair.getClass().getSimpleName() + pair.hashCode(), newCreature.getClass().getSimpleName() + newCreature.hashCode());
            return true;																								// Подтверждаем размножение, бежать в другую локацию искать пару не нужно
        }
        finally
        {
            if (newCreature != null)
                newCreature.getLock().unlock();
            if (pair.getLock().isHeldByCurrentThread())
                pair.getLock().unlock();																					// Отпускаем пару после размножения
        }
    }
}
