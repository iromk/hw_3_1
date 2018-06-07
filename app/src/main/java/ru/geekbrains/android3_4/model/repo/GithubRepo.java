package ru.geekbrains.android3_4.model.repo;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.model.api.ApiHolder;
import ru.geekbrains.android3_4.model.entity.GithubRepository;
import ru.geekbrains.android3_4.model.entity.GithubUser;
import ru.geekbrains.android3_4.model.repo.cache.GithubCache;
import ru.geekbrains.android3_4.model.repo.cache.PaperCache;

public class GithubRepo
{
    private GithubCache cache;

    public GithubRepo(GithubCache cache) {
        this.cache = cache;
    }

    public Observable<GithubUser> getUser(String username)
    {
        return ApiHolder.getApi().getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> {
                    cache.keep(user);
                    return user; })
                .onErrorResumeNext(cache.fetchUser(username));
    }

    public Observable<List<GithubRepository>> getGitRepos(GithubUser user)
    {
        return ApiHolder.getApi().getGitRepos(user.getLogin())
                .subscribeOn(Schedulers.io())
                .map(repositories -> {
                    cache.keep(repositories, user);
                    return repositories; })
                .onErrorResumeNext(cache.fetchRepositories(user));
    }



}
