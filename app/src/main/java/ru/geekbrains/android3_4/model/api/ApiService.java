package ru.geekbrains.android3_4.model.api;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.android3_4.model.entity.GitRepo;
import ru.geekbrains.android3_4.model.entity.User;

public interface ApiService
{
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);

    @GET("/users/{user}/repos")
    Observable<List<GitRepo>> getGitRepos(@Path("user")  String username);
}
