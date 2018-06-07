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
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/7/18.
 */


public class PaperCache implements GithubCache
{
    private static final String BOOK_USERS = "users";

    @Override
    public Observable<GithubUser> fetchUser(String username) {
        return Observable.create(emitter -> {
            if(!Paper.book(BOOK_USERS).contains(username))
            {
                emitter.onError(new RuntimeException("No such user in cache: " + username));
            }
            emitter.onNext(Paper.book(BOOK_USERS).read(username));
            emitter.onComplete();
        });
    }

    @Override
    public void keep(GithubUser user) {
        Paper.book(BOOK_USERS).write(user.getLogin(), user);
    }

    @Override
    public Observable<List<GithubRepository>> fetchRepositories(GithubUser user) {
        return Observable.create(emitter -> {
            Timber.v("fetchRepositories");
            String md5 = Utils.MD5(user.getReposUrl());
            if(!Paper.book("repos").contains(md5))
            {
                emitter.onError(new RuntimeException("No repos for such url: " + user.getReposUrl()));
            }
            emitter.onNext(Paper.book("repos").read(md5));
            emitter.onComplete();
        });
    }

    @Override
    public void keep(List<GithubRepository> gitrepos, GithubUser user) {
        String md5 = Utils.MD5(user.getReposUrl());
        Paper.book("repos").write(md5, gitrepos);
    }
}
