package ru.geekbrains.android3_1.hw3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import io.reactivex.Scheduler;

class ConverterPresenter {

    private final Scheduler mainScheduler;

    public ConverterPresenter(Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
    }

    public void convert(Uri uri) {
        Bitmap b = BitmapFactory.decodeFile(uri.getEncodedPath());

    }

}
