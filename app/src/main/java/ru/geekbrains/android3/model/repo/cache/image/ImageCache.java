package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public interface ImageCache {

    void keep(String url, Bitmap bitmap);
    String fetch(String url);
}
