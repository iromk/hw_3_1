package ru.geekbrains.android3_1;

import ru.geekbrains.android3_1.hw1b.model.CounterModel;
import ru.geekbrains.android3_1.hw1b.presenter.MainPresenter;
import ru.geekbrains.android3_1.hw1b.view.MainView;

/**
 * Created by Roman Syrchin on 5/23/18.
 */
public class FakeView implements MainView {
    final String NO_TEXT = "no text updated";
    String text1 = NO_TEXT;
    String text2 = NO_TEXT;
    String text3 = NO_TEXT;

    final MainPresenter presenter;

    public FakeView() {
        presenter = new MainPresenter(new CounterModel());
        presenter.attachView(this);
    }

    public void pressButton(Buttons b) {
        presenter.buttonClick(b);
    }

    @Override
    public void setButtonOneText(String text) {
        text1 = text;
    }

    @Override
    public void setButtonTwoText(String text) {
        text2 = text;
    }

    @Override
    public void setButtonThreeText(String text) {
        text3 = text;
    }
}
