package ru.geekbrains.android3.model.entity.aa;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
@Table(name = "repositories")
public class AARepository extends Model {
    @Column(name = "github_id")
    public String id;

    @Column(name = "name")
    public String name;

    @Column(name = "user")
    public AAUser user;

    @Column(name = "self_url")
    public String selfUrl;
}
