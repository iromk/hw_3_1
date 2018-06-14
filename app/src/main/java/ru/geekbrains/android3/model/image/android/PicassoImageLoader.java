package ru.geekbrains.android3.model.image.android;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;

public class PicassoImageLoader implements ImageLoader<ImageView>
{
    @Override
    public void loadInto(String url, ImageView container) {
        Picasso.get().load(fixUrl(url)).into(container);
    }

    @Override
    public void loadInto(String url, ImageView container, ImageCache imageCache)
    {

        Picasso.get().load(fixUrl(url)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                container.setImageBitmap(bitmap);
                imageCache.keep(url, bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private String fixUrl(String url) {
        if(url.startsWith("/")) return "file:".concat(url);
        else return url;
    }
}
