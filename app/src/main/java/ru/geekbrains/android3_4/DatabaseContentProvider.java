package ru.geekbrains.android3_4;


import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;

import ru.geekbrains.android3_4.model.entity.aa.AARepository;
import ru.geekbrains.android3_4.model.entity.aa.AAUser;

/**
 * https://github.com/pardom-zz/ActiveAndroid/issues/536
 * Created by Roman Syrchin on 6/7/18.
 */
public class DatabaseContentProvider extends ContentProvider {

    @Override
    protected Configuration getConfiguration() {
        Configuration.Builder builder = new Configuration.Builder(getContext());
        builder.addModelClass(AAUser.class);
        builder.addModelClass(AARepository.class);
        return builder.create();
    }
}
