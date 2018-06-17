package ru.geekbrains.android3.di.modules;

import android.widget.ImageView;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.image.android.GlideImageLoader;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.model.repo.cache.UseCache;
import ru.geekbrains.android3.model.repo.cache.image.AAImageCache;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;
import ru.geekbrains.android3.model.repo.cache.image.NoImageCache;
import ru.geekbrains.android3.model.repo.cache.image.PaperImageCache;
import ru.geekbrains.android3.model.repo.cache.image.RealmImageCache;
import ru.geekbrains.android3.model.repo.cache.image.ZeroImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */

@Module
public class ImageRepoModule {

    @Provides
    public ImageRepo imageRepoDefault(ImageLoader<ImageView> imageLoader) {
        return imageRepoDefaultWithRealmCache(imageLoader);
    }

    @Provides @UseCache
    public ImageRepo imageRepoDefaultWithRealmCache(ImageLoader<ImageView> imageLoader) {
        return new ImageRepo(realmImageCache(), imageLoader);
    }

    @Provides @UseCache("Paper")
    public ImageRepo imageRepoWithPaperCache(ImageLoader<ImageView> imageLoader) {
        return new ImageRepo(papirusOptimus(), imageLoader);
    }

    @Provides @UseCache("AA")
    public ImageRepo imageRepoWithAACache(ImageLoader<ImageView> imageLoader) {
        return new ImageRepo(aaCache(), imageLoader);
    }

    @Provides @NoImageCache
    public ImageRepo imageRepoWithoutCache(ImageLoader<ImageView> imageLoader) {
        return new ImageRepo(noImageCache(), imageLoader);
    }

    @Provides
    public ImageCache realmImageCache() {
        return new RealmImageCache();
    }

    @Provides
    public ImageCache papirusOptimus() {
        return new PaperImageCache();
    }

    @Provides
    public ImageCache aaCache() {
        return new AAImageCache();
    }

    @Provides
    public ImageCache noImageCache() {
        return new ZeroImageCache();
    }

    @Provides
    public ImageLoader<ImageView> imageLoader() {
        return new GlideImageLoader();
    }
}
