package ru.geekbrains.android3.model.image.android;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.android3.model.image.IImageLoader;

public class PicassoImageLoader implements IImageLoader<ImageView>
{
    @Override
    public void loadInto(String url, ImageView container)
    {
        Picasso.get().load("file:"+url).into(container);
    }
}
