package ru.geekbrains.android3_4.model.repo;


import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.App;
import ru.geekbrains.android3_4.model.api.ApiHolder;
import ru.geekbrains.android3_4.model.entity.GithubRepository;
import ru.geekbrains.android3_4.model.entity.GithubUser;
import ru.geekbrains.android3_4.model.repo.cache.GithubCache;
import ru.geekbrains.android3_4.model.repo.cache.PaperCache;
import ru.geekbrains.android3_4.model.utils.Utils;
import timber.log.Timber;

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
        Timber.v("getGitRepos %s", user.getLogin());
        return ApiHolder.getApi().getGitRepos(user.getLogin())
                .subscribeOn(Schedulers.io())
                .map(repositories -> {
                    Timber.v("got repositories");
                    cache.keep(repositories, user);
                    return repositories; })
                .onErrorResumeNext(cache.fetchRepositories(user));
    }


    public Single<String> getAvatarCachedUrl(String avatarUrl) {
        return Single.create(emitter -> {
            Timber.v("getting ava");
            try {
                File cacheDir = new File(App.getInstance().getApplicationContext().getCacheDir()
                        .getAbsolutePath().concat("/github"));
                if(!cacheDir.exists()) cacheDir.mkdir();
                File cacheBitmap = new File(cacheDir, Utils.MD5(avatarUrl) + ".png");
                if(!cacheBitmap.exists()) {
                    Picasso.get().load(avatarUrl).get()
                            .compress(Bitmap.CompressFormat.PNG, 100,
                                    new FileOutputStream(cacheBitmap));
                }
                emitter.onSuccess(cacheBitmap.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
