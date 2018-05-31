package ru.geekbrains.android3_1.hw3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
class BaseImageConverter implements ImageConverter {
    @Override
    public String jpgToPng(InputStream src, OutputStream dst) {
        try {
            Bitmap jpeg = BitmapFactory.decodeStream(src);
            Timber.d(jpeg.toString());
            jpeg.compress(Bitmap.CompressFormat.PNG, 100,
                    dst);

            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dst.toString();
    }


}
