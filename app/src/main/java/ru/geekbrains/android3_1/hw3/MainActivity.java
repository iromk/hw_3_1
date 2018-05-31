package ru.geekbrains.android3_1.hw3;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_3);
        ButterKnife.bind(this);
        Timber.v("%s", getFilesDir());
        Timber.v("%s", Arrays.toString(fileList()));
        Timber.v("%s", getCacheDir());
    }

    @ProvidePresenter
    public ConverterPresenter getConverterPresenter() {
        return new ConverterPresenter(AndroidSchedulers.mainThread());
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

        startActivityForResult(getIntent, PICK_A_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && data.getData() != null && requestCode == PICK_A_PIC) {
//            p.convert(data.getData().getPath());

            try {
                if (!isPermissionGranted()) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    /*ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "title");
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, createTempImageFile());
                    values.put(MediaStore.Images.Media.DESCRIPTION, "description");
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                    Uri outUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    OutputStream os = getContentResolver().openOutputStream(outUri); */// always saves as jpeg
                    OutputStream os = openFileOutput(createTempImageFile(), MODE_PRIVATE);
                    p.convert(is, os);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            p.cancel();
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isPermissionGranted() {
        return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private String createTempImageFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "PNG_" + timeStamp + ".png";
//        final File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return imageFileName;
//        return File.createTempFile(
//                imageFileName,
//                ".png",
//                storageDir
//        ).getPath();
    }

    @Override
    public void reportOnSuccess() {
        Timber.i("reportOnSuccess");
//        try {
//            MediaStore.Images.Media.insertImage(getContentResolver(), pngFile, "biiitmap.png", "png" );
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void reportOnProblem() {

    }

    @Override
    public void reportOnCancel() {

    }
}
