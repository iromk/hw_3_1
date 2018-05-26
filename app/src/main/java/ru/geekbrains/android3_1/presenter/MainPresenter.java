package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function0;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    CounterModel model;

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
        switch (id){
            case ONE:
                model.calculate(id.ordinal())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( (n) -> getViewState().setButtonOneText(n.toString()));
                break;
            case TWO:
                model.calculate(id.ordinal())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( (n) -> getViewState().setButtonTwoText(n.toString()));
                break;
            case THREE:
                model.calculate(id.ordinal())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( (n) -> getViewState().setButtonThreeText(n.toString()));
                break;
        }
    }
}
