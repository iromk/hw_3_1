package ru.geekbrains.android3.model.image;

import ru.geekbrains.android3.model.repo.cache.image.ImageCache;

public interface ImageLoader<T>
{
    void loadInto(String url, T container);
    void loadInto(String url, T container, ImageCache imageCache);
}
