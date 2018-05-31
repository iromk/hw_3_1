package ru.geekbrains.android3_1.hw3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_1.R;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements ConverterView {

    private static final int PICK_A_PIC = 423;
    @InjectPresenter ConverterPresenter p;

    @BindView(R.id.image_view) ImageView imageView;
    private String pngFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_3);
        ButterKnife.bind(this);

    }

    @ProvidePresenter
    public ConverterPresenter getConverterPresenter() {
        return new ConverterPresenter(AndroidSchedulers.mainThread());
    }

    public void onConvertClick(View view) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        startActivityForResult(pickIntent, PICK_A_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && data.getData() != null && requestCode == PICK_A_PIC) {
            try {
                if (!isPermissionGranted()) {
                    requestPermissions();
                } else {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    /*ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "title");
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, createImageFile());
                    values.put(MediaStore.Images.Media.DESCRIPTION, "description");
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                    Uri outUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    OutputStream os = getContentResolver().openOutputStream(outUri); */// always saves as jpeg
                    pngFile = createImageFile();
                    OutputStream os = openFileOutput(createImageFile(), MODE_PRIVATE);
                    p.convert(is, os);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isPermissionGranted() {
        return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
    }

    private String createImageFile() {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "PNG_" + timeStamp + ".png";
    }

    @Override
    public void reportOnStart() {
        Snackbar.make(this.imageView, "Dummy converting...", Snackbar.LENGTH_INDEFINITE)
                .setAction("stop", (View v) ->  p.cancel())
                .show();
    }

    @Override
    public void reportOnSuccess() {
        Timber.i("reportOnSuccess");
        Snackbar.make(this.imageView, "PNG created at private", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void reportOnProblem() {
        Timber.i("reportOnProblem");
    }

    @Override
    public void reportOnCancel() {
        Timber.i("reportOnCancel");
        deleteFile(pngFile);
        Snackbar.make(this.imageView, "You've canceled, not me", Snackbar.LENGTH_LONG).show();
    }
}
