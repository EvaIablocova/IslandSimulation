package com.javarush.island.iablocova.util;

import java.util.concurrent.ThreadLocalRandom;

public class GetRandom {
    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private GetRandom()
    {
    }
}
