package com.javarush.island.iablocova.entity.creatures.animals.predator;

import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;
import com.javarush.island.iablocova.entity.creatures.animals.Predator;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Eagle.yaml")
public class Eagle extends Predator
{
    public Eagle()
    {
        getFoodMap().put(Duck.class, 80);
        getFoodMap().put(Fox.class, 10);
        getFoodMap().put(Mouse.class, 90);
        getFoodMap().put(Rabbit.class, 90);
    }
}
