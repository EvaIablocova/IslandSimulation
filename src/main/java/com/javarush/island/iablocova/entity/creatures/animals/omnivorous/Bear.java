package com.javarush.island.iablocova.entity.creatures.animals.omnivorous;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.Omnivorous;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;
import com.javarush.island.iablocova.entity.creatures.animals.predator.Boa;


@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Bear.yaml")
public class Bear extends Omnivorous
{
    public Bear()
    {
        getFoodMap().put(Boa.class, 80);
        getFoodMap().put(Boar.class, 50);
        getFoodMap().put(Buffalo.class, 20);
        getFoodMap().put(Deer.class, 80);
        getFoodMap().put(Duck.class, 10);
        getFoodMap().put(Goat.class, 70);
        getFoodMap().put(Horse.class, 40);
        getFoodMap().put(Mouse.class, 90);
        getFoodMap().put(Rabbit.class, 80);
        getFoodMap().put(Sheep.class, 70);
    }
}
