package ru.geekbrains.android3.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.UseCache;
import ru.geekbrains.android3.view.MainView;
import ru.geekbrains.android3.view.RepoCardView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    final private Scheduler mainThreadScheduler;
    @Inject @UseCache("AA") GithubRepo githubRepo;
    final private ReposPresenter reposPresenter = new ReposPresenter();
    final private List<GithubRepository> gitReposList = new ArrayList<>();


    public MainPresenter(Scheduler mainThreadScheduler)
    {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
        getViewState().initReposList();
    }

    @SuppressLint("CheckResult")
    private void loadData(){
        githubRepo.getUser("iromk")
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {

                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());

                    githubRepo.getGitRepos(user).singleElement()
                            .observeOn(mainThreadScheduler)
                            .subscribe(repos -> {
                                Timber.v("getGitRepos.subscribe");
                                gitReposList.clear();
                                gitReposList.addAll(repos);
                                getViewState().updateReposList();
                            }, throwable -> {
                                getViewState().showError("Network problem and cache missed requested data while getting repositories.");
                                Timber.e(throwable, "Failed to list repos");});

                }, throwable -> {
                    getViewState().showError("Network problem and cache missed requested data while getting user.");
                    Timber.e(throwable, "Failed to get user");});
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

    public ReposPresenter getReposPresenter() {
        return reposPresenter;
    }

    public void onPermissionsGranted() {
        loadData();
    }

    private class ReposPresenter implements IReposPresenter {
        @Override
        public int getReposCount() {
            return gitReposList.size();
        }

        @Override
        public GithubRepository getRepo(int position) {
            return gitReposList.get(position);
        }

        @Override
        public void representCardView(RepoCardView repoCardView, int position) {
            final GithubRepository repo = reposPresenter.getRepo(position);

            repoCardView.setRepoName(repo.getName());

            if(repo.isFork()) repoCardView.setFork();
            if(repo.isPrivate()) repoCardView.setPrivate();
            if(repo.isStarred()) repoCardView.setStarred();
        }
    }
}
