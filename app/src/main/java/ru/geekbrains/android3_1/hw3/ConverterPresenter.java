package ru.geekbrains.android3_1.hw3;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class ConverterPresenter extends MvpPresenter<ConverterView> {

    private final Scheduler mainScheduler;
    private Disposable converterStream;

    public ConverterPresenter(Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
    }

    public void cancel() {
        if(converterStream != null && !converterStream.isDisposed()) converterStream.dispose();
    }

    public void convert(InputStream is, OutputStream os) {

        Timber.i(is.toString());
        getViewState().reportOnStart();
        ImageConverter ic = ImageConverterFactory.create();
        converterStream = ic.jpgToPng(is, os)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .doOnDispose(() -> getViewState().reportOnCancel())
                .doOnComplete(() -> getViewState().reportOnSuccess())
                .subscribe();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }
}
