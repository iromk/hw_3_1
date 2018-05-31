package ru.geekbrains.android3_1.hw3;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
public interface ImageConverter {

    int JPG_TO_PNG = 1;

    String jpgToPng(InputStream src, OutputStream dst);

}
