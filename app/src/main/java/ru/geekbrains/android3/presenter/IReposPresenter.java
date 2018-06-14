package ru.geekbrains.android3.presenter;

import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.view.RepoCardView;

/**
 * Created by Roman Syrchin on 6/2/18.
 */
public interface IReposPresenter {

    int getReposCount();
    GithubRepository getRepo(int position);
    void representCardView(RepoCardView repoCardView, int position);
}
