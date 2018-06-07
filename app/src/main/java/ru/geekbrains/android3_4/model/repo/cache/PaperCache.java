package ru.geekbrains.android3_4.model.repo.cache;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.model.api.ApiHolder;
import ru.geekbrains.android3_4.model.entity.GithubRepository;
import ru.geekbrains.android3_4.model.entity.GithubUser;
import ru.geekbrains.android3_4.model.utils.NetworkStatus;
import ru.geekbrains.android3_4.model.utils.Utils;

/**
 * Created by Roman Syrchin on 6/7/18.
 */


public class PaperCache implements GithubCache
{

    private static final String BOOK_USERS = "users";

    @Override
    public Observable<GithubUser> fetchUser(String username) {
        if(!Paper.book(BOOK_USERS).contains(username))
        {
            return Observable.error(new RuntimeException("No such user in cache: " + username));
        }

        return Observable.fromCallable(() -> Paper.book(BOOK_USERS).read(username));
    }

    @Override
    public void keep(GithubUser user) {
        Paper.book(BOOK_USERS).write(user.getLogin(), user);
    }

    @Override
    public Observable<List<GithubRepository>> fetchRepositories(GithubUser user) {
        String md5 = Utils.MD5(user.getReposUrl());
        if(!Paper.book("repos").contains(md5))
        {
            return Observable.error(new RuntimeException("No repos for such url: " + user.getReposUrl()));
        }

        return Observable.fromCallable(() -> Paper.book("repos").read(md5));
    }

    @Override
    public void keep(List<GithubRepository> gitrepos, GithubUser user) {
        String md5 = Utils.MD5(user.getReposUrl());
        Paper.book("repos").write(md5, gitrepos);
    }
}
