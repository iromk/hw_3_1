package ru.geekbrains.android3_4.model.repo;


import io.reactivex.Observable;
import ru.geekbrains.android3_4.model.api.ApiHolder;
import ru.geekbrains.android3_4.model.entity.User;

public class UsersRepo
{
    public Observable<User> getUser(String username)
    {
        return ApiHolder.getApi().getUser(username);
    }


}
