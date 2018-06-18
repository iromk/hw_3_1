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

import static org.junit.Assert.assertEquals;

/**
 * Created by Roman Syrchin on 6/18/18.
 */
public class GithubRepoInstrumentedTest {

    private static MockWebServer mockWebServer;
    @Inject GithubRepo githubRepo;

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
    public void getUser()
    {
        mockWebServer.enqueue(createUserResponse("somelogin", "someurl"));

        TestObserver<GithubUser> observer = new TestObserver<>();
        githubRepo.getUser("somelogin").subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(),"somelogin");
        assertEquals(observer.values().get(0).getAvatarUrl(), "someurl");
    }

    private MockResponse createUserResponse(String login, String avatarUrl)
    {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse().setBody(body);
    }
}
