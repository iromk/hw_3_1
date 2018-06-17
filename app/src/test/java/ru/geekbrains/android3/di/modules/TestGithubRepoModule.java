package ru.geekbrains.android3.di.modules;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.UseCache;

/**
 * Created by Roman Syrchin on 6/17/18.
 */

@Module
public class TestGithubRepoModule {

    @Provides @UseCache("AA")
    public GithubRepo getRepo() {
        return Mockito.mock(GithubRepo.class);
    }

}
