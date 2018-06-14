package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

import io.reactivex.annotations.Nullable;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class ZeroImageCache implements ImageCache {
    @Override
    public void keep(String url, Bitmap bitmap) {

    }

    @Override @Nullable
    public String fetch(String url) {
        return url;
    }
}
