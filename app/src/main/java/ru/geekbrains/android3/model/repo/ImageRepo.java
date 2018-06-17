package ru.geekbrains.android3.model.repo;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class ImageRepo {

    private ImageCache imageCache;
    private ImageLoader<ImageView> imageLoader;

    public ImageRepo(ImageCache imageCache, ImageLoader<ImageView> imageLoader) {
        this.imageCache = imageCache;
        this.imageLoader = imageLoader;
    }

    @SuppressLint("CheckResult")
    public void loadInto(String url, ImageView imageView) {
        final String cachedImage = imageCache.fetch(url);

        if(cachedImage == null) {
           imageLoader.loadInto(url, imageView, imageCache);
        } else {
            imageLoader.loadInto(cachedImage, imageView);
        }
    }
}
