package ru.geekbrains.android3_1.hw2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import ru.geekbrains.android3_1.R;

/**
 * Created by Roman Syrchin on 5/27/18.
 */
public class MainActivity extends AppCompatActivity implements BurgerBar {


    @BindView(R.id.tv_queue_size) TextView queueSize;
    private Manager m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_2);
        ButterKnife.bind(this);

        m = new Manager(this).manage();

    }

    @OnClick(R.id.btn_open_door)
    public void onOpenDoor(View v) {
        Observable.interval(5, TimeUnit.SECONDS).subscribe(m);
        v.setEnabled(false);
    }

    @OnClick(R.id.btn_make_food)
    public void onMakeBurger() {
    }


    @Override
    public void setTextQueueSize(String s) {
        queueSize.setText(s);
    }
}
