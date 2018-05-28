package ru.geekbrains.android3_1.hw1b.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_1.hw1b.model.CounterModel;
import ru.geekbrains.android3_1.hw1b.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    final private CounterModel model;

    public MainPresenter(CounterModel model)
    {
        Timber.i("MainPresenter");
        this.model = model;
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
    }

    public void buttonClick(MainView.Buttons id)
    {
        final Single<Integer> observe = model.calculateHard(id.ordinal())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        switch (id) {
            case ONE:
                observe.subscribe( (cnt) -> getViewState().setButtonOneText(cnt.toString()));
                break;
            case TWO:
                observe.subscribe( (cnt) -> getViewState().setButtonTwoText(cnt.toString()));
                break;
            case THREE:
                observe.subscribe( (cnt) -> getViewState().setButtonThreeText(cnt.toString()));
                break;
        }
    }
}
