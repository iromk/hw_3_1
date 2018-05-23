package ru.geekbrains.android3_1.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.geekbrains.android3_1.R;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.presenter.MainPresenter;

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public  MainPresenter provideMainPresenter()
    {
        return new MainPresenter(new CounterModel());
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onButtonClick(Button button)
    {
        presenter.buttonClick(button.getId());
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
