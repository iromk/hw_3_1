package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

import java.io.File;

import io.paperdb.Paper;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class PaperImageCache extends FileImageCache implements ImageCache {

    private static final String BOOK_IMAGES = "images";

    @Override
    public void keep(String url, Bitmap bitmap) {
        final String key = makeKey(url);
        final File cachedBitmap = saveImageToFile(bitmap, key);
        Timber.v("paper keeps at %s", cachedBitmap.toString());
        Paper.book(BOOK_IMAGES).write(key, cachedBitmap.toString());
    }

    @Override
    public String fetch(String url) {
        Timber.v("unpapering image if possible");
        return Paper.book(BOOK_IMAGES).read(makeKey(url), null);
    }

    @Override
    String getCacheName() {
        return "paper";
    }
}
