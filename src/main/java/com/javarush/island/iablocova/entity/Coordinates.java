package com.javarush.island.iablocova.entity;

import lombok.Data;

@Data
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
