package com.javarush.island.iablocova.view;

import com.javarush.island.iablocova.entity.CustomData;
import com.javarush.island.iablocova.entity.Island;
import com.javarush.island.iablocova.entity.Statistics;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;

import java.util.concurrent.BrokenBarrierException;

public interface View {
    CustomData getCustomerParameters() throws InterruptedException;
    void printStatistics(Statistics statistics) throws InterruptedException;

    void setIsland(Island island) throws InterruptedException;

    void synchronize() throws BrokenBarrierException, InterruptedException;

    void movePicture(Creature thisCreature, Cell oldCell);

    void deletePicture(Creature creature);

    void showPicture(Creature creature);
}
