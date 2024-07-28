package com.javarush.island.iablocova.entity;

import lombok.Getter;

@Getter
public class CustomData {
    private final int width;
    private final int height;
    private final long dayLength;

    public CustomData(int width, int height, long dayLength)
    {
        this.width = width;
        this.height = height;
        this.dayLength = dayLength;
    }
}
