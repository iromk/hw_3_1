package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    CounterModel model;

    public MainPresenter(CounterModel model)
    {
        this.model = model;
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
    }

    public void buttonClick(MainView.Buttons id)
    {
        final int value = model.calculate(id.ordinal());
        switch (id){
            case ONE:
                getViewState().setButtonOneText(value + "");
                break;
            case TWO:
                getViewState().setButtonTwoText(value + "");
                break;
            case THREE:
                getViewState().setButtonThreeText(value + "");
                break;
        }
    }
}
