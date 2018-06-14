package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class PaperImageCache implements ImageCache {

    @Override
    public void keep(String url, Bitmap bitmap) {

    }

    @Override
    public String fetch(String url) {
        return null;
    }
}
