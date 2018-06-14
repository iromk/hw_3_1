package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;

import java.io.File;

import io.realm.Realm;
import ru.geekbrains.android3.model.entity.realm.CachedImage;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class RealmImageCache extends FileImageCache implements ImageCache {

    @Override
    public void keep(String url, Bitmap bitmap) {
        final String key = makeKey(url);
        final File cachedBitmap = saveImageToFile(bitmap, key);

        Timber.v(cachedBitmap.toString());
        Realm.getDefaultInstance().executeTransactionAsync(realm ->
        {
            CachedImage cachedImage = new CachedImage();
            cachedImage.setUrl(url);
            cachedImage.setPath(cachedBitmap.toString());
            realm.copyToRealm(cachedImage);
        });
    }

    @Override
    String getCacheName() {
        return "realm";
    }

    @Override
    public String fetch(String url) {
        Timber.v("fetching...");
        final CachedImage cachedImage = Realm.getDefaultInstance()
                .where(CachedImage.class)
                .equalTo("url", url)
                .findFirst();

        if (cachedImage != null) {
            Timber.v("...from [%s]", cachedImage.getPath());
            return cachedImage.getPath();
        }
        return null;
    }

//
//    private boolean contains(String url) {
//        return Realm.getDefaultInstance().where(CachedImage.class).equalTo("url", url).count() > 0;
//    }
}
