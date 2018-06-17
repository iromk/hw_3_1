package ru.geekbrains.android3.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3.presenter.MainPresenter;
import ru.geekbrains.android3.di.modules.TestGithubRepoModule;

/**
 * Created by Roman Syrchin on 6/17/18.
 */
@Singleton
@Component(modules = { TestGithubRepoModule.class })
public interface TestComponent {

    void inject(MainPresenter p);

}
