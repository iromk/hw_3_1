package ru.geekbrains.android3;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import ru.geekbrains.android3.di.DaggerTestComponent;
import ru.geekbrains.android3.di.modules.TestGithubRepoModule;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.presenter.MainPresenter;
import ru.geekbrains.android3.view.MainView;
import ru.geekbrains.android3.di.TestComponent;

/**
 * Created by Roman Syrchin on 6/17/18.
 */
public class MainPresenterUnitTest {

    private MainPresenter p;
    @Mock MainView v;
    private TestScheduler testScheduler;

    @BeforeClass
    public static void setupClass() {
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        p = Mockito.spy(new MainPresenter(testScheduler));
    }

    @Test
    public void onFirstViewAttach() {
        p.attachView(v);
        Mockito.verify(v).initReposList();
        Mockito.verifyNoMoreInteractions(v);
    }

    @Test
    public void loadData_Success() {
        GithubUser user = new GithubUser("iromk", "avatar.png", "http/repos");

        TestComponent testComponent = DaggerTestComponent.builder()
                .testGithubRepoModule(new TestGithubRepoModule() {
                    @Override
                    public GithubRepo getRepo() {
                        GithubRepo repo = super.getRepo();
                        Mockito.when(repo.getUser(user.getLogin())).thenReturn(Observable.just(user));
                        Mockito.when(repo.getGitRepos(user)).thenReturn(Observable.just(new ArrayList<>()));
                        return repo;
                    }
                }).build();
        testComponent.inject(p);

        p.attachView(v);
        p.onPermissionsGranted();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(v).initReposList();
        Mockito.verify(v).setUsernameText(user.getLogin());
        Mockito.verify(v).loadImage(user.getAvatarUrl());
        Mockito.verify(v).updateReposList();
        Mockito.verifyNoMoreInteractions(v);
    }


}
