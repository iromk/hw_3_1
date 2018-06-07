package ru.geekbrains.android3_4.model.repo.cache;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3_4.model.entity.GithubRepository;
import ru.geekbrains.android3_4.model.entity.GithubUser;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
public interface GithubCache {

    Observable<GithubUser> fetch(String username);
    void keep(GithubUser user);

    Observable<List<GithubRepository>> fetch(GithubUser user);
    void keep(List<GithubRepository> gitrepos, GithubUser user);
}
