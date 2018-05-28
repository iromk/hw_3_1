package ru.geekbrains.android3_1.hw2;


import io.reactivex.Observable;
import io.reactivex.Observer;
import timber.log.Timber;

/**
 * Created by Roman Syrchin on 5/27/18.
 */
public class Entrance {


    public Observable<Customer> openDoor() {
        return new Observable<Customer>() {
            @Override
            protected void subscribeActual(Observer<? super Customer> observer) {
                Timber.v("subscribed");
                observer.onNext(new Customer());
                try {
                    java.util.concurrent.TimeUnit.SECONDS.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Timber.v("timed out");
            }
        };
    }

}
