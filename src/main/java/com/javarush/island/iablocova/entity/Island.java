package com.javarush.island.iablocova.entity;

import com.javarush.island.iablocova.constants.Paths;
import com.javarush.island.iablocova.entity.creatures.Creature;
import com.javarush.island.iablocova.entity.island.Cell;
import com.javarush.island.iablocova.repository.SetOfCreaturesTypes;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;

public class Island
{
    private final Cell[][] cells;
    private final CustomData customData;

    static
    {
        try (DirectoryStream<Path> listOfHerbivorous = Files.newDirectoryStream(Paths.HERBIVOROUS);
             DirectoryStream<Path> listOfOmnivorous = Files.newDirectoryStream(Paths.OMNIVOROUS);
             DirectoryStream<Path> listOfPredators = Files.newDirectoryStream(Paths.PREDATORS);
             DirectoryStream<Path> listOfPlants = Files.newDirectoryStream(Paths.PLANTS))
        {
            addCreaturesClass(listOfHerbivorous);
            addCreaturesClass(listOfOmnivorous);
            addCreaturesClass(listOfPredators);
            addCreaturesClass(listOfPlants);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static void addCreaturesClass(DirectoryStream<Path> files) throws ClassNotFoundException					// Формирование перечня всех видов животных, обитающих на острове, отдельно для растений, травоядных, всеядных, хищников
    {
        for(Path p : files)
        {
            String s = p.toString();
            if(s.endsWith(".java"))
            {
                String s1 = s.replaceAll("[\\\\/]", ".").substring(14, s.length() - 5);
                System.out.println("class " + s1);
                Class<?> clazz = Class.forName(s1);
                SetOfCreaturesTypes.SET_OF_CREATURES_TYPES.add((Class<? extends Creature>) clazz);
            }
        }
    }

    public Island(CustomData customData)
    {
        this.customData = customData;
        int width = customData.getWidth();
        int height = customData.getHeight();

        cells = new Cell[height][width];																						// Создание массива локаций острова и последующая инициализация каждой локации
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                cells[y][x] = new Cell(new Coordinates(x, y), cells);
                for(Class<? extends Creature> clazz : SetOfCreaturesTypes.SET_OF_CREATURES_TYPES)						// TODO рассмотреть вынесение listOfCreaturesTypes из отдельного класса в класс Island
                {
                    cells[y][x].getCellCreatures().put(clazz, Collections.synchronizedSet(new HashSet<>()));
                }
            }
        }

        for (int y = 0; y < height; y++)																					// Создание списка соседних локаций для каждой отдельной локации
        {
            for (int x = 0; x < width; x++)
            {
                for (int i = -1; i <= 1; i++)
                {
                    for (int j = -1; j <= 1; j++)
                    {
                        if (y + j >= 0 &&
                                y + j < height &&
                                x + i >= 0 &&
                                x + i < width && !(i == 0 && j == 0))
                            cells[y][x].getNeighbors().add(cells[y + j][x + i]);
                    }
                }
            }
        }
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public CustomData getCustomData()
    {
        return customData;
    }
}
