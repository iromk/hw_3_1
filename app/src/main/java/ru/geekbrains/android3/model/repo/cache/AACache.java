package ru.geekbrains.android3.model.repo.cache;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.entity.aa.AARepository;
import ru.geekbrains.android3.model.entity.aa.AAUser;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
public class AACache implements GithubCache {
    @Override
    public Observable<GithubUser> fetchUser(String username) {
        return Observable.create(emitter -> {
            AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", username)
                    .executeSingle();

            if (aaUser == null)
            {
                emitter.onError(new RuntimeException("No such user in cache: " + username));
            }
            else
            {
                Timber.v("fetchUser from AA");
                emitter.onNext(new GithubUser(aaUser.login, aaUser.avatarUrl, aaUser.reposUrl));
                emitter.onComplete();
            }
        });
    }

    @Override
    public void keep(GithubUser user) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", user.getLogin())
                .executeSingle();

        if (aaUser == null)
        {
            aaUser = new AAUser();
            aaUser.login = user.getLogin();
        }

        aaUser.avatarUrl = user.getAvatarUrl();
        aaUser.save();
    }

    @Override
    public Observable<List<GithubRepository>> fetchRepositories(GithubUser user) {
        return Observable.create(emitter -> {
            AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", user.getLogin())
                    .executeSingle();


            if (aaUser == null)
            {
                emitter.onError(new RuntimeException("No such user in cache: " + user.getLogin()));
            }
            else
            {
                Timber.v("fetchRepositories from AA");
                List<GithubRepository> repos = new ArrayList<>();
                for(AARepository aaRepository : aaUser.repositories())
                {
                    repos.add(new GithubRepository(aaRepository.id, aaRepository.name));
                }
                emitter.onNext(repos);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void keep(List<GithubRepository> gitrepos, GithubUser user) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", user.getLogin())
                .executeSingle();

        if (aaUser == null)
        {
            aaUser = new AAUser();
            aaUser.login = user.getLogin();
            aaUser.avatarUrl = user.getAvatarUrl();
            aaUser.save();
        }

        new Delete().from(AARepository.class).where("user = ?", aaUser.getId()).execute();

        ActiveAndroid.beginTransaction();
        try
        {
            for (GithubRepository repository : gitrepos)
            {
                AARepository aaRepository = new AARepository();
                aaRepository.id = repository.getId();
                aaRepository.name = repository.getName();
                aaRepository.user = aaUser;
                aaRepository.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally
        {
            ActiveAndroid.endTransaction();
        }
    }
}
