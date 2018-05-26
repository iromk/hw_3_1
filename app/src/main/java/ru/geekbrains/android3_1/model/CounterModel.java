package ru.geekbrains.android3_1.model;

import android.icu.util.TimeUnit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;



public class CounterModel
{
    List<Integer> counters;

    Observable<Integer> cc;

    public CounterModel()
    {

        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }


    public Observable<Integer> calculate(int index) {
        int value = counters.get(index) + 1;
        counters.set(index, value);
        return new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer observer) {
                try {
                    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                observer.onNext(value);
            }
        };
    }

}
