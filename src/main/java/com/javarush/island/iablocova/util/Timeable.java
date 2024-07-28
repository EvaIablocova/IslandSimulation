package com.javarush.island.iablocova.util;

import java.time.Instant;

public interface Timeable {
    default long getMillis(Instant start, Instant stop)
    {
        int nanos1 = stop.getNano() - start.getNano();
        long seconds = stop.getEpochSecond() - start.getEpochSecond();
        int nanos2 = nanos1;
        if (nanos1 < 0)
        {
            nanos2 = nanos1 + 1_000_000_000;
            seconds -= 1;
        }
        return seconds * 1000 + nanos2 / 1_000_000;
    }
}
