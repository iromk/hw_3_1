package ru.geekbrains.android3_1.hw1a;

import timber.log.Timber;

/**
 * Created by Roman Syrchin on 5/25/18.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
