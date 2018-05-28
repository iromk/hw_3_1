package ru.geekbrains.android3_1.hw2

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by Roman Syrchin on 5/28/18.
 */
class Manager(private val bb: BurgerBar) : Observer<Any> {


    override fun onError(e: Throwable) {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onComplete() {
    }

    override fun onNext(t: Any) {
        Timber.v("next customer")
        bar.onNext(Client())
    }

    class Burger {}

    val bar: PublishSubject<Client>

    init {
        bar = PublishSubject.create<Client>()
    }

    class Client {
        var want: Int = (Math.random() * 3 + 1).toInt()
    }

    fun manage(): Manager {

        bar.map { x -> x.want.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bb::setTextQueueSize)

        bar.timeInterval()

        return this
    }

}
