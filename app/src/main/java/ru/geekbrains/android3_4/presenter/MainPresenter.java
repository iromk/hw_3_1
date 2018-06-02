package ru.geekbrains.android3_4.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3_4.model.repo.UsersRepo;
import ru.geekbrains.android3_4.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    Scheduler mainThreadScheduler;
    UsersRepo usersRepo;

    public MainPresenter(Scheduler mainThreadScheduler)
    {
        this.mainThreadScheduler = mainThreadScheduler;
        usersRepo = new UsersRepo();
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
        loadData();
    }

    @SuppressLint("CheckResult")
    private void loadData(){
        usersRepo.getUser("iromk")
//        usersRepo.getUser("AntonZarytski")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {

                    //TODO: получить и отобразить список репозиториев пользователя

                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());

                    usersRepo.getGitRepos(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(mainThreadScheduler)
                            .subscribe(repos -> getViewState().setUsernameText(repos.get(0).getName()));

                }, throwable -> Timber.e(throwable, "Failed to get user"));
    }

    private void getDataViaOkHttp()
    {
        Single<String> single = Single.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/users/AntonZarytski")
                    .build();
            return client.newCall(request).execute().body().string();
        });

        single.subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(s -> Timber.d(s));

    }
}
