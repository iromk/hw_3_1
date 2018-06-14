package ru.geekbrains.android3.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3.R;
import ru.geekbrains.android3.model.image.IImageLoader;
import ru.geekbrains.android3.model.image.android.PicassoImageLoader;
import ru.geekbrains.android3.presenter.MainPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.rv_repos)
    RecyclerView reposRecyclerView;

    @InjectPresenter
    MainPresenter presenter;

    IImageLoader<ImageView> imageLoader;
    private ReposRVAdapter gitReposAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoader =
                new PicassoImageLoader();
//                new GlideImageLoader();
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter()
    {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    public void setUsernameText(String username)
    {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url)
    {
       imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void initReposList() {
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gitReposAdapter = new ReposRVAdapter(presenter.getReposPresenter());
        reposRecyclerView.setAdapter(gitReposAdapter);
    }

    @Override
    public void updateReposList() {
        gitReposAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(CharSequence errorText) {
        Snackbar.make(avatarImageView, errorText, Snackbar.LENGTH_INDEFINITE)
                .setAction("gotcha", (View v) -> {})
                .show();
    }
}
