package ru.geekbrains.android3_1.hw3;

import io.reactivex.annotations.Nullable;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
public class ImageConverterFactory {

    @Nullable
    public static ImageConverter create(int type) {
        if(type == ImageConverter.JPG_TO_PNG) {
            return new BaseImageConverter();
        }
        return null;
    }

}
