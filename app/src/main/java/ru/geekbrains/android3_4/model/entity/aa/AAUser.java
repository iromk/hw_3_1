package ru.geekbrains.android3_4.model.entity.aa;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
@Table(name = "users")
public class AAUser extends Model {

    @Column(name = "login")
    public String login;

    @Column(name = "avatar_url")
    public String avatarUrl;

    @Column(name = "repos_url")
    public String reposUrl;

    public List<AARepository> repositories()
    {
        return getMany(AARepository.class, "user");
    }
}
