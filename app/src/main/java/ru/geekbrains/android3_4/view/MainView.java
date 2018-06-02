package ru.geekbrains.android3_4.view;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView
{
    void setUsernameText(String username);
    void loadImage(String url);

    void initReposList();
    void updateReposList();
}
