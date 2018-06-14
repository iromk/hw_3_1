package ru.geekbrains.android3.di.modules;

import android.widget.ImageView;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.image.android.GlideImageLoader;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.model.repo.cache.image.AAImageCache;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;
import ru.geekbrains.android3.model.repo.cache.image.NoImageCache;
import ru.geekbrains.android3.model.repo.cache.image.PaperImageCache;
import ru.geekbrains.android3.model.repo.cache.image.RealmImageCache;
import ru.geekbrains.android3.model.repo.cache.UseCache;
import ru.geekbrains.android3.model.repo.cache.image.ZeroImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */

@Module
public class ImageRepoModule {

    @Provides
    public ImageRepo imageRepo() {
        ImageRepo imageRepo = new ImageRepo();
        App.getInstance().getAppComponent().inject(imageRepo);
        return imageRepo;
    }

    @Provides @UseCache
    public ImageCache realmImageCache() {
        return new RealmImageCache();
    }

    @Provides @UseCache("Paper")
    public ImageCache papirusOptimus() {
        return new PaperImageCache();
    }

    @Provides @UseCache("AA")
    public ImageCache aaCache() {
        return new AAImageCache();
    }

    @Provides @NoImageCache
    public ImageCache noImageCache() {
        return new ZeroImageCache();
    }

    @Provides
    public ImageLoader<ImageView> imageLoader() {
        return new GlideImageLoader();
    }
}
