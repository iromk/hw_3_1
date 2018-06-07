package ru.geekbrains.android3_4.model.entity;

import com.google.gson.annotations.Expose;

public class GithubUser
{
    @Expose String avatarUrl;
    @Expose String login;
    @Expose String reposUrl;

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
