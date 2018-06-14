package ru.geekbrains.android3.model.repo.cache;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.entity.GithubUser;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
public interface GithubCache {

    Observable<GithubUser> fetchUser(String username);
    void keep(GithubUser user);

    Observable<List<GithubRepository>> fetchRepositories(GithubUser user);
    void keep(List<GithubRepository> gitrepos, GithubUser user);
}
