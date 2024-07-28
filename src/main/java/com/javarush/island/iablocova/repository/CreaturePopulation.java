package com.javarush.island.iablocova.repository;

import java.util.HashMap;

public final class CreaturePopulation
{
    public static final HashMap<String, Integer> population = new HashMap<>();

    static
    {
        population.put("Bear", 5 * 2);
        population.put("Boa", 15 * 2);
        population.put("Boar", 5 * 2);
        population.put("Buffalo", 10 * 2);
        population.put("Caterpillar", 1000);
        population.put("Deer", 15 * 2);
        population.put("Duck", 20 * 2);
        population.put("Eagle", 10 * 2);
        population.put("Fox", 15 * 2);
        population.put("Goat", 30 * 2);
        population.put("Horse", 20 * 2);
        population.put("Mouse", 500);
        population.put("Plant", 1000);
        population.put("Rabbit", 30 * 2);
        population.put("Sheep", 20 * 2);
        population.put("Wolf", 15 * 2);
    }

    private CreaturePopulation()
    {
    }
}
