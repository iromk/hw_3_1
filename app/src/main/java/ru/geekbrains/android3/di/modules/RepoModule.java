package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.AACache;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.PaperCache;
import ru.geekbrains.android3.model.repo.cache.RealmCache;
import ru.geekbrains.android3.model.repo.cache.UseCache;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule
{
    @Provides
    public GithubRepo githubRepoDefault(ApiService api) {
        return githubRepoRealm(api);
    }

    @Provides @UseCache("Paper")
    public GithubRepo githubRepoPapirus(ApiService api) {
        return new GithubRepo(paper(), api);
    }

    @Provides @UseCache
    public GithubRepo githubRepoRealm(ApiService api) {
        return new GithubRepo(realm(), api);
    }

    @Provides @UseCache("AA")
    public GithubRepo githubRepoAAcache(ApiService api) {
        return new GithubRepo(aa(), api);
    }

    @Provides
    public GithubCache realm() {
        return new RealmCache();
    }

    @Provides
    public GithubCache aa() {
        return new AACache();
    }

    @Provides
    public GithubCache paper() {
        return new PaperCache();
    }
}
