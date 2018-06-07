package ru.geekbrains.android3_4.model.entity;

import com.google.gson.annotations.Expose;

public class GithubUser
{
    @Expose String login;
    @Expose String avatarUrl;
    @Expose String reposUrl;

    public GithubUser(String login, String avatarUrl, String reposUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public String getReposUrl() { return reposUrl; }

    public String getLogin()
    {
        return login;
    }
}
