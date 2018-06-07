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
    GithubCache cache = new PaperCache();




    public Observable<GithubUser> getUser(String username)
    {
        return ApiHolder.getApi().getUser(username)
                .subscribeOn(Schedulers.io())
                .map(user -> {
                    cache.keep(user);
                    return user; })
                .onErrorResumeNext(cache.fetch(username));
    }

    public Observable<List<GithubRepository>> getGitRepos(GithubUser user)
    {
        return ApiHolder.getApi().getGitRepos(user.getLogin());
    }



}
