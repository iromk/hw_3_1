package ru.geekbrains.android3_1.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView
{
    void setButtonOneText(String text);
    void setButtonTwoText(String text);
    void setButtonThreeText(String text);
}
