package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

import com.activeandroid.query.Select;

import java.io.File;

import ru.geekbrains.android3.model.entity.aa.AACachedImage;
import ru.geekbrains.android3.model.entity.aa.AAUser;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class AAImageCache extends FileImageCache implements ImageCache {
    @Override
    String getCacheName() {
        return "AA";
    }

    @Override
    public void keep(String url, Bitmap bitmap) {
        final String key = makeKey(url);
        final File cachedBitmap = saveImageToFile(bitmap, key);
        Timber.v("saving with AA %s", cachedBitmap.toString());

        AACachedImage cache = new Select()
                .from(AACachedImage.class)
                .where("key = ?", key)
                .executeSingle();

        if (cache == null) cache = new AACachedImage();

        cache.key = key;
        cache.url = url;

        cache.save();

    }

    @Override
    public String fetch(String url) {
        AACachedImage cachedImage = new Select()
                .from(AACachedImage.class)
                .where("key = ?", makeKey(url))
                .executeSingle();

        Timber.v("trying to get image from AA...");
        if (cachedImage == null) return null;

        Timber.v("...gotcha image from AA");
        return getFilePath(cachedImage.key);
    }
}
