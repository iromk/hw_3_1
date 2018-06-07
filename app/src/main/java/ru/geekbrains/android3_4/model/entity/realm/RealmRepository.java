package ru.geekbrains.android3_4.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
public class RealmRepository extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
