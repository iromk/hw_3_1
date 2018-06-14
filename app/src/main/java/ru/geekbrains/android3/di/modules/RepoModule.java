package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.AACache;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.PaperCache;
import ru.geekbrains.android3.model.repo.cache.RealmCache;
import ru.geekbrains.android3.model.repo.cache.image.UseCache;

@Module(includes = {ApiModule.class, CacheModule.class, ImageRepoModule.class})
public class RepoModule
{
    @Provides
    public GithubRepo githubRepo() {
        GithubRepo githubRepo = new GithubRepo();
        App.getInstance().getAppComponent().inject(githubRepo);
        return githubRepo;
    }

    @Provides @UseCache
    public GithubCache realm() {
        return new RealmCache();
    }

    @Provides @UseCache("AA")
    public GithubCache aa() {
        return new AACache();
    }

    @Provides @UseCache("Paper")
    public GithubCache paper() {
        return new PaperCache();
    }
}
