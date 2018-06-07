package ru.geekbrains.android3_4;

import android.app.Application;

import timber.log.Timber;

public class App extends Application
{
    private static App instance = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
    }

    public static App getInstance() {
        return instance;
    }

}
