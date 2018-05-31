package ru.geekbrains.android3_1.hw3;

import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
public interface ImageConverter {

    int JPG_TO_PNG = 1;

    Observable jpgToPng(InputStream src, OutputStream dst);

}
