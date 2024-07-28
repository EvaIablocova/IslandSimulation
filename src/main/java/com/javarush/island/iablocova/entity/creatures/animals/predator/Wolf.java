package com.javarush.island.iablocova.entity.creatures.animals.predator;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;
import com.javarush.island.iablocova.entity.creatures.animals.Predator;
import com.javarush.island.iablocova.entity.creatures.animals.omnivorous.Boar;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Wolf.yaml")
public class Wolf extends Predator implements Runnable
{
    public Wolf()
    {
        getFoodMap().put(Boar.class, 15);
        getFoodMap().put(Buffalo.class, 10);
        getFoodMap().put(Deer.class, 15);
        getFoodMap().put(Duck.class, 40);
        getFoodMap().put(Goat.class, 60);
        getFoodMap().put(Horse.class, 10);
        getFoodMap().put(Mouse.class, 80);
        getFoodMap().put(Rabbit.class, 60);
        getFoodMap().put(Sheep.class, 70);
    }
}
