package ru.geekbrains.android3;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.geekbrains.android3.di.DaggerTestComponent;
import ru.geekbrains.android3.di.TestComponent;
import ru.geekbrains.android3.di.modules.ApiModule;
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
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule()
                {
                    @Override
                    public String baseUrl()
                    {
                        return mockWebServer.url("/").toString();
                    }
                }).build();

        component.inject(this);
    }

    @Test
    public void getCachedUserFromPaper()
    {
        final GithubUser user = new GithubUser("somelogin", "someurl", "reposurl");

        mockSuccessGithubUserResponse(user);

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        GithubCache cache = new PaperCache();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();
        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    @Test
    public void getCachedUserFromRealm()
    {
        final GithubUser user = new GithubUser("someloginRealm", "someurlRealm", "reposurl");

        mockSuccessGithubUserResponse(user);

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        GithubCache cache = new RealmCache();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();
        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    @Test
    public void getCachedUserFromAA()
    {
        final GithubUser user = new GithubUser("someloginAA", "someurlAA", "reposurl");

        mockSuccessGithubUserResponse(user);

        TestObserver<GithubUser> cacheObserver = new TestObserver<>();
        GithubCache cache = new AACache();
        cache.fetchUser(user.getLogin()).subscribe(cacheObserver);
        cacheObserver.awaitTerminalEvent();
        assertEquals(cacheObserver.values().get(0).getLogin(), user.getLogin());
        assertEquals(cacheObserver.values().get(0).getAvatarUrl(), user.getAvatarUrl());
    }

    private void mockSuccessGithubUserResponse(GithubUser user) {
        mockWebServer.enqueue(createUserResponse(user.getLogin(), user.getAvatarUrl()));
        TestObserver<GithubUser> observer = new TestObserver<>();
        githubRepoWithAA.getUser(user.getLogin()).subscribe(observer);
        observer.awaitTerminalEvent();
    }

    private MockResponse createUserResponse(String login, String avatarUrl)
    {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse().setBody(body);
    }
}
