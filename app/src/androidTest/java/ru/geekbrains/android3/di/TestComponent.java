package ru.geekbrains.android3.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3.GithubRepoInstrumentedTest;
import ru.geekbrains.android3.di.modules.RepoModule;

/**
 * Created by Roman Syrchin on 6/17/18.
 */
@Singleton
@Component(modules = { RepoModule.class })
public interface TestComponent {

    void inject(GithubRepoInstrumentedTest test);

}
