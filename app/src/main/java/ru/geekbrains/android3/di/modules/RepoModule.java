package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.GithubCache;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule
{
    @Provides
    public GithubRepo usersRepo(GithubCache cache, ApiService api){
        return new GithubRepo(cache, api);
    }
}
