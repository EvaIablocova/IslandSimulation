package com.javarush.island.iablocova.entity.creatures.animals.predator;


import com.javarush.island.iablocova.entity.creatures.animals.Predator;
import com.javarush.island.iablocova.abstraction.Config;
import com.javarush.island.iablocova.entity.creatures.animals.herbivorous.*;

@Config(yamlFile = "src/main/java/com/javarush/island/kavtasyev/config/creatures/animals/Boa.yaml")
public class Boa extends Predator
{
    public Boa()
    {
        getFoodMap().put(Duck.class, 10);
        getFoodMap().put(Fox.class, 15);
        getFoodMap().put(Mouse.class, 40);
        getFoodMap().put(Rabbit.class, 20);
    }
}
