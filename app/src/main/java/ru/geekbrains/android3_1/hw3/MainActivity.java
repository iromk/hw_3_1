package ru.geekbrains.android3_1.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arellomobile.mvp.MvpView;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_1.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MvpView {

    private static final int PICK_A_PIC = 423;
    private ConverterPresenter p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_3);
        Timber.v("%s", getFilesDir());
        Timber.v("%s", Arrays.toString(fileList()));
        Timber.v("%s", getCacheDir());

        p = new ConverterPresenter(AndroidSchedulers.mainThread());

    }

    public void onConvertClick(View view) {
//        Intent pick = new Intent(Intent.ACTION_PICK);
//        pick.setType("image/*");
//        pick.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(pick, "Select Picture"), 1);
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(pickIntent, PICK_A_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_A_PIC) {
            p.convert(data.getData());
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}
