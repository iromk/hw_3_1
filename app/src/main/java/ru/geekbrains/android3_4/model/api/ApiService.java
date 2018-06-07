package ru.geekbrains.android3_4.model.api;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.android3_4.model.entity.GithubRepository;
import ru.geekbrains.android3_4.model.entity.GithubUser;

public interface ApiService
{
    @GET("/users/{user}")
    Observable<GithubUser> getUser(@Path("user") String username);

    @GET("/users/{user}/repos")
    Observable<List<GithubRepository>> getGitRepos(@Path("user")  String username);
}
