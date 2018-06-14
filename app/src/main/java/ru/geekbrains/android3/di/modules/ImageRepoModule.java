package ru.geekbrains.android3.di.modules;

import android.widget.ImageView;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.model.image.ImageLoader;
import ru.geekbrains.android3.model.image.android.PicassoImageLoader;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.model.repo.cache.image.ImageCache;
import ru.geekbrains.android3.model.repo.cache.image.RealmImageCache;

/**
 * Created by Roman Syrchin on 6/14/18.
 */

@Module
public class ImageRepoModule {

    @Provides
    public ImageRepo imageRepo() {
        final ImageRepo imageRepo = new ImageRepo();
        App.getInstance().getAppComponent().inject(imageRepo);
        return imageRepo;
    }

    @Provides
    public ImageCache imageCache() {
        return new RealmImageCache();
    }

    @Provides
    public ImageLoader<ImageView> imageLoader() {
        return new PicassoImageLoader();
    }
}
