package ru.geekbrains.android3.model.image;

public interface IImageLoader<T>
{
    void loadInto(String url, T container);
}
