package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.repo.GithubRepo;

@Module(includes = {ApiModule.class, CacheModule.class, ImageCacheModule.class})
public class RepoModule
{
    @Provides
    public GithubRepo usersRepo() {
        GithubRepo githubRepo = new GithubRepo();
        App.getInstance().getAppComponent().inject(githubRepo);
        return githubRepo;
    }
}
