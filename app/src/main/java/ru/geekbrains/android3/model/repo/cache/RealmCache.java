package ru.geekbrains.android3.model.repo.cache;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.geekbrains.android3.model.entity.GithubRepository;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.entity.realm.RealmRepository;
import ru.geekbrains.android3.model.entity.realm.RealmUser;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 6/7/18.
 */
public class RealmCache implements GithubCache {
    @Override
    public Observable<GithubUser> fetchUser(String username) {
        return Observable.create(emitter -> {

            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
            Timber.v("fetchUser RealmUser");
            if(realmUser == null)
            {
                emitter.onError(new RuntimeException("No such user: " + username));
            }
            else
            {
                emitter.onNext(new GithubUser(
                            realmUser.getLogin(),
                            realmUser.getAvatarUrl(),
                            realmUser.getReposUrl()));
                emitter.onComplete();
            }
        });
    }

    @Override
    public void keep(GithubUser user) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        Timber.v("keep RealmUser");
        if(realmUser == null)
        {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
            });
        }
        else
        {
            realm.executeTransaction(innerRealm -> realmUser.setAvatarUrl(user.getAvatarUrl()));
        }
        realm.close();
    }

    @Override
    public Observable<List<GithubRepository>> fetchRepositories(GithubUser user) {
        return Observable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
            if(realmUser == null)
            {
                emitter.onError(new RuntimeException("No such user: " + user.getLogin()));
            }
            else
            {
                Timber.v("fetch repos from Realm");
                List<GithubRepository> repositories = new ArrayList<>();
                if(realmUser.getRepos().size() == 0)
                    emitter.onError(new RuntimeException("No repos in cache for user " + user.getLogin()));

                for(RealmRepository realmRepository : realmUser.getRepos())
                {
                    repositories.add(new GithubRepository(realmRepository.getId(), realmRepository.getName()));
                }
                emitter.onNext(repositories);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void keep(List<GithubRepository> gitrepos, GithubUser user) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        if(realmUser == null)
        {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
            });
        }
        final RealmUser finalRealmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        realm.executeTransaction(innerRealm -> {
            finalRealmUser.getRepos().deleteAllFromRealm();
            Timber.v("keeping repos in realm");
            for(GithubRepository repository : gitrepos)
            {
                RealmRepository realmRepository = innerRealm.createObject(RealmRepository.class, repository.getId());
                realmRepository.setName(repository.getName());
                finalRealmUser.getRepos().add(realmRepository);
            }
        });

        realm.close();
    }
}
