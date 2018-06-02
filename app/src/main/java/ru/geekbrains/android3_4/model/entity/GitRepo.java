package ru.geekbrains.android3_4.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Roman Syrchin on 6/2/18.
 */

public class GitRepo {

    @Expose String name;
    @Expose String fullName;
    @Expose @SerializedName("private") boolean privateFlag;
    @Expose boolean fork;
    private boolean starred = false;


    public String getName() { return name; }

    public String getFullName() { return fullName; }

    public boolean isPrivate() { return privateFlag; }

    public boolean isFork() { return fork; }

    public boolean isStarred() { return starred; }
    public void setStarred() { starred = true; }
}
