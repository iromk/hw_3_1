package ru.geekbrains.android3.model.repo.cache.image;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface NoImageCache {

}
