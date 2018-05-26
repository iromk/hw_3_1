package ru.geekbrains.android3_1.hw1b.model;

import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Single;
import timber.log.Timber;


public class CounterModel
{
    final private CopyOnWriteArrayList<Integer> counters;

    public CounterModel()
    {
        counters = new CopyOnWriteArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Single<Integer> calculateHard(int index) {
        return Single.create(emitter -> {
            try {
                final long deep = (long)(Math.random() * 8.0);
                Timber.v("Sleeping for %d", deep);
                java.util.concurrent.TimeUnit.SECONDS.sleep(deep);

                Timber.v("Awaken after %d", deep);
                final int value = counters.get(index) + 1;
                counters.set(index, value);
                emitter.onSuccess(value);

            } catch (InterruptedException e) {
                emitter.onError(e);
            }
        });
    }

}
