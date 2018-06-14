package ru.geekbrains.android3.model.entity.aa;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Roman Syrchin on 6/14/18.
 */
@Table(name = "cachedimages")
public class AACachedImage extends Model {

    @Column public String url;
    @Column public String key;

}
