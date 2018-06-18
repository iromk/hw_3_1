package ru.geekbrains.android3.di.modules;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.UseCache;

/**
 * Created by Roman Syrchin on 6/18/18.
 */
@Module(includes = { ApiModule.class })
public class TestGithubRepoModule {

    @Provides
    @UseCache("Mock")
    public GithubRepo githubRepoWithMockedCache(ApiService api) {
        return new GithubRepo(mockCache(), api);
    }

    @Mock GithubCache mockedCache;

    @Provides
    public GithubCache mockCache() {
        MockitoAnnotations.initMocks(this);
        return mockedCache;
    }


}
