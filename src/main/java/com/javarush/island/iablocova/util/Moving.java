package com.javarush.island.iablocova.util;

import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.creatures.animals.Herbivorous;
import com.javarush.island.iablocova.entity.creatures.animals.Omnivorous;
import com.javarush.island.iablocova.entity.creatures.animals.Predator;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.view.View;

import java.util.ArrayList;

public class Moving
{

    private Moving()
    {
    }

    public static void move(Creature thisCreature)																		//TODO решить, как синхронизировать списки животных в разных локациях
    {
        if (thisCreature.getSpeed() == 0)																				// Если скорость животного равна нулю, то животное никуда не перемещается. Оно неподвижно
            return;

        Cell currentCell = thisCreature.getCurrentCell();
        Cell oldCell = new Cell(currentCell.getCoordinates(), currentCell.getAllCells());
        ArrayList<Cell> neighbors = currentCell.getNeighbors();
        int i = GetRandom.RANDOM.nextInt(neighbors.size());
        Cell newCell = neighbors.get(i);

        if (newCell.getCellCreatures().get(thisCreature.getClass()).size() < thisCreature.getMaxNumberPerCell())		// Если в ячейке животных меньше максимально допустимого количества, тогда передвигаемся в эту ячейку TODO заблокировать список животных в новой локации
        {
            newCell.getCellCreatures().get(thisCreature.getClass()).add(thisCreature);
            currentCell.getCellCreatures().get(thisCreature.getClass()).remove(thisCreature);
            thisCreature.setCurrentCell(newCell);
        }

        View view = thisCreature.getView();
        view.movePicture(thisCreature, oldCell);

        if (thisCreature instanceof Predator)
            thisCreature.setWantToEat(Math.min(thisCreature.getWantToEat() * 1.05, thisCreature.getMassOfFood()));		// Если животное - хищник, то оно тратит на перемещение 10 % энергии.
        else if (thisCreature instanceof Omnivorous)
            thisCreature.setWantToEat(Math.min(thisCreature.getWantToEat() * 1.04, thisCreature.getMassOfFood()));		// Если животное - всеядное, то оно тратит на перемещение 8 % энергии.
        else if (thisCreature instanceof Herbivorous)
            thisCreature.setWantToEat(Math.min(thisCreature.getWantToEat() * 1.02, thisCreature.getMassOfFood()));		// Если животное - травоядное, то оно тратит на перемещение 2 % энергии
        System.out.printf("Животное %1$s переместилось из ячейки %2$s в ячейку %3$s%n", thisCreature.getClass().getSimpleName() + thisCreature.hashCode(), oldCell.getCoordinates().toString(), newCell.getCoordinates().toString());
    }
}

