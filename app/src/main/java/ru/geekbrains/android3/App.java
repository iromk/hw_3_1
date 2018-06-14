package ru.geekbrains.android3;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import io.paperdb.Paper;
import io.realm.Realm;
import ru.geekbrains.android3.di.AppComponent;
import ru.geekbrains.android3.di.DaggerAppComponent;
import ru.geekbrains.android3.di.modules.AppModule;
import ru.geekbrains.android3.model.entity.aa.AARepository;
import ru.geekbrains.android3.model.entity.aa.AAUser;
import timber.log.Timber;

public class App extends Application
{
    private static App instance = null;

    private AppComponent appComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        Realm.init(this);
        ActiveAndroid.initialize(this);

        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
