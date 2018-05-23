package ru.geekbrains.android3_1.model;

import java.util.ArrayList;
import java.util.List;

public class CounterModel
{
    List<Integer> counters;

    public CounterModel()
    {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public int calculate(int index){
        int value = counters.get(index) + 1;
        counters.set(index, value);
        return value;
    }
}
