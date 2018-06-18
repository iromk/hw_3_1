package ru.geekbrains.android3.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3.GithubRepoInstrumentedTest;
import ru.geekbrains.android3.RepoCacheInstrumentedTest;
import ru.geekbrains.android3.di.modules.ApiModule;
import ru.geekbrains.android3.di.modules.RepoModule;
import ru.geekbrains.android3.di.modules.TestGithubRepoModule;
import ru.geekbrains.android3.model.api.ApiService;

/**
 * Created by Roman Syrchin on 6/17/18.
 */
@Singleton
@Component(modules = { RepoModule.class, TestGithubRepoModule.class})
public interface TestComponent {

    void inject(GithubRepoInstrumentedTest test);
    void inject(RepoCacheInstrumentedTest test);

}
