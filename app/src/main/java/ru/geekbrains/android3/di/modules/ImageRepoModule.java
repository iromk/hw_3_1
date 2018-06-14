package ru.geekbrains.android3.di.modules;

import android.widget.ImageView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.image.android.PicassoImageLoader;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;
import ru.geekbrains.android3.model.repo.cache.image.NoImageCache;
import ru.geekbrains.android3.model.repo.cache.image.PaperImageCache;
import ru.geekbrains.android3.model.repo.cache.image.RealmImageCache;
import ru.geekbrains.android3.model.repo.cache.image.UseImageCache;
import ru.geekbrains.android3.model.repo.cache.image.ZeroImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */

@Module
public class ImageRepoModule {

    @Provides
    public ImageRepo imageRepo(@UseImageCache ImageCache imageCache, ImageLoader<ImageView> imageLoader) {
        return new ImageRepo(imageCache, imageLoader);
    }

    @Provides @UseImageCache
    public ImageCache realmImageCache() {
        return new RealmImageCache();
    }

    @Provides @UseImageCache("Paper")
    public ImageCache papirusOptimus() {
        return new PaperImageCache();
    }

    @Provides @NoImageCache
    public ImageCache noImageCache() {
        return new ZeroImageCache();
    }

    @Provides
    public ImageLoader<ImageView> imageLoader() {
        return new PicassoImageLoader();
    }
}
