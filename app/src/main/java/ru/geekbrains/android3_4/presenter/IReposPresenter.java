package ru.geekbrains.android3_4.presenter;

import ru.geekbrains.android3_4.model.entity.GitRepo;
import ru.geekbrains.android3_4.view.RepoCardView;

/**
 * Created by Roman Syrchin on 6/2/18.
 */
public interface IReposPresenter {

    int getReposCount();
    GitRepo getRepo(int position);
    void representCardView(RepoCardView repoCardView, int position);
}
