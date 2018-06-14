package ru.geekbrains.android3.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3.presenter.MainPresenter;
import ru.geekbrains.android3.view.MainActivity;

@Singleton
@Component(modules = {/*AppModule.class, RepoModule.class*/})
public interface AppComponent
{
    void inject(MainActivity activity);
    void inject(MainPresenter activity);
}
