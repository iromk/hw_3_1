package ru.geekbrains.android3.model.repo.cache.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.utils.Utils;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
abstract class FileImageCache {

    File saveImageToFile(Bitmap bitmap, String filename) {
        final File cacheDir = getCacheDir();
        final File cacheBitmap = new File(cacheDir, filename.concat(".PNG"));
        try {
            FileOutputStream fos = new FileOutputStream(cacheBitmap);
            bitmap.compress(Bitmap.CompressFormat.PNG,
                    100,
                    fos);
            fos.close();

        } catch (IOException e) {
            Timber.e(e);
        }
        return cacheBitmap;
    }

    @Nullable
    private File getCacheDir() {
        final File cacheDir = new File(getCachePath());
        if(!cacheDir.exists() && cacheDir.mkdirs())
            return cacheDir;
        else return null;
    }

    private String getCachePath() {
        return App.getInstance().getApplicationContext().getCacheDir()
                .getAbsolutePath().concat("/images/").concat(getCacheName());
    }

    String makeKey(String plain) {
        return Utils.MD5(plain);
    }

    abstract String getCacheName();


}

