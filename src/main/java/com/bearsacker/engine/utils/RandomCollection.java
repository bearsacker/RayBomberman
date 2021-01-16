package com.bearsacker.engine.utils;

import java.util.NavigableMap;
import java.util.TreeMap;

public class RandomCollection<T> {

    private final NavigableMap<Double, T> map = new TreeMap<Double, T>();

    private Double total = 0d;

    public RandomCollection<T> add(double weight, T result) {
        if (weight <= 0d) {
            return this;
        }

        total += weight;
        map.put(total, result);

        return this;
    }

    public T next() {
        double value = NumberGenerator.get().randomDouble() * total;

        return map.higherEntry(value).getValue();
    }
}
