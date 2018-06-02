package ru.geekbrains.android3_4.presenter;

import android.annotation.SuppressLint;
import android.widget.ListView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3_4.model.entity.GitRepo;
import ru.geekbrains.android3_4.model.repo.UsersRepo;
import ru.geekbrains.android3_4.view.MainView;
import ru.geekbrains.android3_4.view.RepoCardView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    Scheduler mainThreadScheduler;
    UsersRepo usersRepo;
    private ReposPresenter reposPresenter = new ReposPresenter();

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
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {

                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());

                    usersRepo.getGitRepos(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(mainThreadScheduler)
                            .subscribe(repos -> {
                                gitReposList = repos;
                                getViewState().initReposList();
                                getViewState().updateReposList();
                    });

                }, throwable -> Timber.e(throwable, "Failed to get user"));
    }

    private List<GitRepo> gitReposList;

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

    public ReposPresenter getReposPresenter() {
        return reposPresenter;
    }

    private class ReposPresenter implements IReposPresenter {
        @Override
        public int getReposCount() {
            return gitReposList.size();
        }

        @Override
        public String getRepoName(int position) {
            return gitReposList.get(position).getName();
        }

        @Override
        public void representCardView(RepoCardView repoCardView, int position) {
            repoCardView.setRepoName(reposPresenter.getRepoName(position));
        }
    }
}
