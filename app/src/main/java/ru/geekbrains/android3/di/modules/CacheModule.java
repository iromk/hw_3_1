package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.RealmCache;

@Module
public class CacheModule
{
    @Provides
    public GithubCache cache()
    {
        return new RealmCache();
    }
}
