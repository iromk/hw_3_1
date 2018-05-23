package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.geekbrains.android3_1.R;
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

    public void buttonClick(int id)
    {
        int value = -1;
        switch (id){
            case R.id.btn_one:
                value = model.calculate(0);
                getViewState().setButtonOneText(value + "");
                break;
            case R.id.btn_two:
                value = model.calculate(1);
                getViewState().setButtonTwoText(value + "");
                break;
            case R.id.btn_three:
                value = model.calculate(2);
                getViewState().setButtonThreeText(value + "");
                break;
        }
    }
}
