package com.javarush.island.iablocova.entity.creatures.animals.omnivorous;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.Omnivorous;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;
import com.javarush.island.iablocova.entity.creatures.plants.Plant;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Boar.yaml")
public class Boar extends Omnivorous
{
    public Boar()
    {
        getFoodMap().put(Caterpillar.class, 90);
        getFoodMap().put(Mouse.class, 50);
        getFoodMap().put(Plant.class, 100);
    }
}
