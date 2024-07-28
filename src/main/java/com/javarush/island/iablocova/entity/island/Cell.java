package com.javarush.island.iablocova.entity.island;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.javarush.island.iablocova.entity.Coordinates;
import com.javarush.island.iablocova.entity.creatures.Creature;
import javafx.scene.layout.StackPane;
import lombok.Getter;

@Getter
public class Cell extends StackPane
{
    private final Coordinates coordinates;
    private final Cell[][] allCells;
    private final ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> cellCreatures = new ConcurrentHashMap<>();
    private final ArrayList<Cell> neighbors = new ArrayList<>();

    public Cell(Coordinates coordinates, Cell[][] allCells)
    {
        this.allCells = allCells;
        this.coordinates = coordinates;
    }

    public ArrayList<Cell> getNeighbors()
    {
        return neighbors;
    }

    public ConcurrentHashMap<Class<? extends Creature>, Set<Creature>> getCellCreatures()
    {
        return cellCreatures;
    }

    public Cell[][] getAllCells()
    {
        return allCells;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }
}
