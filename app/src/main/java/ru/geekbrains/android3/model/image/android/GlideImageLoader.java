package ru.geekbrains.android3.model.image.android;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;

public class GlideImageLoader implements ImageLoader<ImageView>
{
    @Override
    public void loadInto(String url, ImageView container)
    {
        GlideApp.with(container.getContext()).asBitmap().load(url).into(container);
    }

    @Override
    public void loadInto(String url, ImageView container, ImageCache imageCache)
    {
        GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource)
            {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource)
            {
                imageCache.keep(url, resource);
                return false;
            }
        }).into(container);
    }

}
