package ru.geekbrains.android3;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.geekbrains.android3.di.DaggerTestComponent;
import ru.geekbrains.android3.di.TestComponent;
import ru.geekbrains.android3.di.modules.ApiModule;
import ru.geekbrains.android3.model.api.ApiService;
import ru.geekbrains.android3.model.entity.GithubUser;
import ru.geekbrains.android3.model.repo.GithubRepo;
import ru.geekbrains.android3.model.repo.cache.AACache;
import ru.geekbrains.android3.model.repo.cache.GithubCache;
import ru.geekbrains.android3.model.repo.cache.PaperCache;
import ru.geekbrains.android3.model.repo.cache.RealmCache;
import ru.geekbrains.android3.model.repo.cache.UseCache;

import static org.junit.Assert.assertEquals;

/**
 * Created by Roman Syrchin on 6/18/18.
 */
public class RepoCacheInstrumentedTest {

    private static MockWebServer mockWebServer;
    @Inject @UseCache("Paper") GithubRepo githubRepoWithPaper;
    @Inject @UseCache GithubRepo githubRepoWithRealm;
    @Inject @UseCache("AA") GithubRepo githubRepoWithAA;
//    @Mock GithubCache githubCache;
//    @Inject ApiService apiService;
    @Inject @UseCache("Mock") GithubRepo githubRepoWithMockedCache;

    @BeforeClass
    public static void setupClass() throws IOException
    {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException
    {
        mockWebServer.shutdown();
    }

    @Before
    public void setup()
    {
//        MockitoAnnotations.initMocks(this);

        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule()
                {
                    @Override
                    public String baseUrl()
                    {
                        return mockWebServer.url("/").toString();
                    }
                })
                .build();

        component.inject(this);
    }

    @Test @Ignore
    public void githubRepoKeepsInCache() {
        final GithubUser user = new GithubUser("mocking", "bird", "the");
        mockWebServer.enqueue(createUserResponse(user.getLogin(), user.getAvatarUrl()));
        TestObserver<GithubUser> observer = new TestObserver<>();
        githubRepoWithMockedCache.getUser(user.getLogin()).subscribe(observer);
        observer.awaitTerminalEvent();

        Mockito.verify(githubRepoWithMockedCache.cache).keep(user);
    }

    @Test
    public void cacheUserAndFetchFromPaper()
    {
        final GithubUser user = new GithubUser("somelogin", "someurl", "reposurl");

        mockWebServer.enqueue(createUserResponse(user.getLogin(), user.getAvatarUrl()));
        TestObserver<GithubUser> observer = new TestObserver<>();
        githubRepoWithPaper.getUser(user.getLogin()).subscribe(observer);
        observer.awaitTerminalEvent();
        GithubCache cache = new PaperCache();

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();
        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    @Test
    public void keepAndFetchFromRealmWorks()
    {
        final GithubUser user = new GithubUser("someloginRealm", "someurlRealm", "reposurl");

        GithubCache cache = new RealmCache();
        cache.keep(user);

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();

        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    @Test
    public void cacheUserAndFetchFromAA()
    {
        final GithubUser user = new GithubUser("someloginAA", "someurlAA", "reposurl");
        mockWebServer.enqueue(createUserResponse(user.getLogin(), user.getAvatarUrl()));
        TestObserver<GithubUser> observer = new TestObserver<>();
        githubRepoWithAA.getUser(user.getLogin()).subscribe(observer);
        observer.awaitTerminalEvent();

        GithubCache cache = new AACache();

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();
        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    private MockResponse createUserResponse(String login, String avatarUrl)
    {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse().setBody(body);
    }
}
