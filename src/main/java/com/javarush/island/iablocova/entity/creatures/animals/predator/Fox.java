package com.javarush.island.iablocova.entity.creatures.animals.predator;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;
import com.javarush.island.iablocova.entity.creatures.animals.Predator;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Fox.yaml")
public class Fox extends Predator
{
    public Fox()
    {
        getFoodMap().put(Caterpillar.class, 40);
        getFoodMap().put(Duck.class, 60);
        getFoodMap().put(Mouse.class, 90);
        getFoodMap().put(Rabbit.class, 70);
    }
}
