package ru.geekbrains.android3_1.hw3;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Roman Syrchin on 5/31/18.
 */
public interface ConverterView extends MvpView {

    void reportOnSuccess();
    void reportOnProblem();
    void reportOnCancel();
}
