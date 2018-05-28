package ru.geekbrains.android3_1.hw1b.view;

import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_1.R;
import ru.geekbrains.android3_1.hw1b.model.CounterModel;
import ru.geekbrains.android3_1.hw1b.presenter.MainPresenter;

import static ru.geekbrains.android3_1.hw1b.view.MainView.Buttons.ONE;
import static ru.geekbrains.android3_1.hw1b.view.MainView.Buttons.THREE;
import static ru.geekbrains.android3_1.hw1b.view.MainView.Buttons.TWO;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @BindView(R.id.btn_one)
    Button buttonOne;

    @BindView(R.id.btn_two)
    Button buttonTwo;

    @BindView(R.id.btn_three)
    Button buttonThree;

    @InjectPresenter MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1b);
        ButterKnife.bind(this);
        setupTags();
    }

    private void setupTags() {
        buttonOne.setTag(R.id.button_tag, ONE);
        buttonTwo.setTag(R.id.button_tag, TWO);
        buttonThree.setTag(R.id.button_tag, THREE);
    }

    @ProvidePresenter
    public  MainPresenter provideMainPresenter()
    {
        return new MainPresenter(new CounterModel(), AndroidSchedulers.mainThread());
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onButtonClick(Button button)
    {
        presenter.buttonClick((Buttons) button.getTag(R.id.button_tag));
    }


    @Override
    public void setButtonOneText(String text)
    {
        buttonOne.setText(text);
    }

    @Override
    public void setButtonTwoText(String text)
    {
        buttonTwo.setText(text);
    }

    @Override
    public void setButtonThreeText(String text)
    {
        buttonThree.setText(text);
    }
}
