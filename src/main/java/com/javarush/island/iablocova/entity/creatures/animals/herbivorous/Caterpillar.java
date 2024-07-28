package com.javarush.island.iablocova.entity.creatures.animals.herbivorous;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.Herbivorous;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Caterpillar.yaml")
public class Caterpillar extends Herbivorous
{
    public Caterpillar()
    {
        getFoodMap().put(Plant.class, 100);
    }
}
