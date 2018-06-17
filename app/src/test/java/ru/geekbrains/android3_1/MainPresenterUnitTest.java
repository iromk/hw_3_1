package ru.geekbrains.android3_1;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.schedulers.TestScheduler;
import ru.geekbrains.android3.presenter.MainPresenter;
import ru.geekbrains.android3.view.MainView;

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
        p.onPermissionsGranted();
        Mockito.verify(v).initReposList();
        Mockito.verifyNoMoreInteractions(v);
    }
}
