package ru.geekbrains.android3.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;
import ru.geekbrains.android3.model.repo.cache.image.RealmImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */

@Module
public class ImageCacheModule {

    @Provides
    public ImageCache imageCache() {
        return new RealmImageCache();
    }
}
