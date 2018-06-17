package ru.geekbrains.android3.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3.App;
import ru.geekbrains.android3.R;
import ru.geekbrains.android3.model.repo.ImageRepo;
import ru.geekbrains.android3.model.repo.cache.UseCache;
import ru.geekbrains.android3.model.repo.cache.image.NoImageCache;
import ru.geekbrains.android3.presenter.MainPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    private static final int PERMISSIONS_REQUEST_ID = 0;

    private static final String[] permissons = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };


    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.rv_repos)
    RecyclerView reposRecyclerView;

    @InjectPresenter MainPresenter presenter;

    @Inject @UseCache("Paper") ImageRepo imageRepo;

    private ReposRVAdapter gitReposAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.getInstance().getAppComponent().inject(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter()
    {
        MainPresenter presenter = new MainPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void setUsernameText(String username)
    {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url)
    {
       imageRepo.loadInto(url, avatarImageView);
    }

    @Override
    public void initReposList() {
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reposRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        gitReposAdapter = new ReposRVAdapter(presenter.getReposPresenter());
        reposRecyclerView.setAdapter(gitReposAdapter);
        checkPermissions();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    onPermissionsGranted();
                }
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.permissons_required)
                            .setMessage(R.string.permissions_required_message)
                            .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                            .setOnCancelListener(dialog -> requestPermissions())
                            .create()
                            .show();
                }
            }
        }
    }

    private void checkPermissions()
    {
        for(String permission : permissons)
        {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions();
                return;
            }
        }

        onPermissionsGranted();
    }

    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(this, permissons, PERMISSIONS_REQUEST_ID);
    }

    private void onPermissionsGranted()
    {
        presenter.onPermissionsGranted();
    }
}
