package ru.geekbrains.android3.model.repo;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3.model.api.ApiHolder;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import timber.log.Timber;

public class GithubRepo
{
    @Inject GithubCache cache;
    @Inject ApiService apiService;

    public Maybe<GithubUser> getUser(String username)
    {
        return ApiHolder.getApi().getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> {
                    cache.keep(user);
                    return user; })
                .onErrorResumeNext(cache.fetchUser(username))
                .singleElement();
    }

    public Observable<List<GithubRepository>> getGitRepos(GithubUser user)
    {
        Timber.v("getGitRepos %s", user.getLogin());
        return ApiHolder.getApi().getGitRepos(user.getLogin())
                .subscribeOn(Schedulers.io())
                .map(repositories -> {
                    Timber.v("got repositories");
                    cache.keep(repositories, user);
                    return repositories; })
                .onErrorResumeNext(cache.fetchRepositories(user));
    }

}
