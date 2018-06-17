package ru.geekbrains.android3.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3.di.modules.AppModule;
import ru.geekbrains.android3.di.modules.ImageRepoModule;
import ru.geekbrains.android3.di.modules.RepoModule;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.presenter.MainPresenter;
import ru.geekbrains.android3.view.MainActivity;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageRepoModule.class})
public interface AppComponent
{
    void inject(MainActivity activity);
    void inject(MainPresenter presenter);
}
