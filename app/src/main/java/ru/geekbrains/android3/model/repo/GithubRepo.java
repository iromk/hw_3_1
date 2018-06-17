package ru.geekbrains.android3.model.repo;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3.model.api.ApiHolder;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.UseCache;
import ru.geekbrains.android3.model.utils.NetworkStatus;
import timber.log.Timber;

public class GithubRepo
{
    private GithubCache cache;
    private ApiService apiService;

    public GithubRepo(GithubCache cache, ApiService apiService) {
        this.cache = cache;
        this.apiService = apiService;
    }

    public Observable<GithubUser> getUser(String username)
    {
        if(NetworkStatus.isOffline())
            return cache.fetchUser(username);
        else
            return apiService.getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> {
                    cache.keep(user);
                    return user; })
                .onErrorResumeNext(cache.fetchUser(username));
    }

    public Observable<List<GithubRepository>> getGitRepos(GithubUser user)
    {
        Timber.v("getGitRepos %s", user.getLogin());
        if(NetworkStatus.isOffline())
            return cache.fetchRepositories(user);
        else
            return apiService.getGitRepos(user.getLogin())
                .subscribeOn(Schedulers.io())
                .map(repositories -> {
                    Timber.v("got repositories");
                    cache.keep(repositories, user);
                    return repositories; })
                .onErrorResumeNext(cache.fetchRepositories(user));
    }

}
