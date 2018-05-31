package ru.geekbrains.android3_1.hw3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.OutputStream;


import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
class BaseImageConverter implements ImageConverter {
    @Override
    public Observable jpgToPng(InputStream src, OutputStream dst) {
        return Observable.create(emitter -> {
            try {
                Bitmap jpeg = BitmapFactory.decodeStream(src);
                Timber.d(jpeg.toString());
                Thread.sleep(3333);
                jpeg.compress(Bitmap.CompressFormat.PNG, 100, dst);
                emitter.onComplete();
            } catch (InterruptedException e) {
                Timber.i("Just awaken from sleep");
            } finally {
                src.close();
                dst.close();
            }
        });
    }


}
