package ru.geekbrains.android3_1.hw3;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
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
        ImageConverter ic = ImageConverterFactory.create(ImageConverter.JPG_TO_PNG);
        if(ic != null)
            converterStream = Observable.fromCallable(
                    () -> ic.jpgToPng(is, os))
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainScheduler)
                    .doOnDispose(() -> getViewState().reportOnCancel())
                    .doOnComplete(() -> getViewState().reportOnSuccess())
                    .doOnError(throwable -> getViewState().reportOnCancel())
                    .subscribe();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }
}
