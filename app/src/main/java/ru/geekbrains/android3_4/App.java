package ru.geekbrains.android3_4;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import io.paperdb.Paper;
import io.realm.Realm;
import ru.geekbrains.android3_4.model.entity.aa.AARepository;
import ru.geekbrains.android3_4.model.entity.aa.AAUser;
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
        Paper.init(this);
        Realm.init(this);
        ActiveAndroid.initialize(this);
    }

    public static App getInstance() {
        return instance;
    }

}
