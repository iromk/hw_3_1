package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.entity.realm.CachedImage;
import ru.geekbrains.android3.model.utils.Utils;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
public class RealmImageCache implements ImageCache {

    @Override
    public void keep(String url, Bitmap bitmap) {
        final File cacheDir = getCacheDir();
        final File cacheBitmap = new File(cacheDir, Utils.MD5(url) + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(cacheBitmap);
            bitmap.compress(Bitmap.CompressFormat.PNG,
                    100,
                    fos);
            fos.close();

        } catch (IOException e) {
            Timber.e(e);
        }

        Timber.v(cacheBitmap.toString());
        Realm.getDefaultInstance().executeTransactionAsync(realm ->
        {
            CachedImage cachedImage = new CachedImage();
            cachedImage.setUrl(url);
            cachedImage.setPath(cacheBitmap.toString());
            realm.copyToRealm(cachedImage);
        });
    }

    @NonNull
    private File getCacheDir() {
        final File cacheDir = new File(getCachePath());
        if(!cacheDir.exists()) cacheDir.mkdir();
        return cacheDir;
    }

    private String getCachePath() {
        return App.getInstance().getApplicationContext().getCacheDir()
                .getAbsolutePath().concat("/github");
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
