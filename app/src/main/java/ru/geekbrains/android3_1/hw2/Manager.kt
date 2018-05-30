package ru.geekbrains.android3_1.hw2

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit

/**
 * Created by Roman Syrchin on 5/28/18.
 */
class Manager(private val bb: BurgerBar, private val mainThreadScheduler: Scheduler) : Observer<Any> {


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

    val burgers : CopyOnWriteArrayList<Burger>

    class Burger {}

    class BurgerCooker {
        fun cook() : Burger {
            return Burger()
        }
    }

    val bar: PublishSubject<Client>

    init {
        bar = PublishSubject.create<Client>()
        burgers = CopyOnWriteArrayList<Burger>()
    }

    class Client {
        var burgersOrdered: Int = (Math.random() * 3 + 1).toInt()
    }

    fun opensTheDoor() {
        Observable.interval(5, TimeUnit.SECONDS).subscribe(this)
    }

    fun manage(): Manager {

        burgers.add(Burger())
        burgers.add(Burger())
        burgers.add(Burger())
        burgers.add(Burger())

        bar
            .observeOn(mainThreadScheduler)
            .map { x -> x.burgersOrdered}
            .subscribe(::w)
//                .subscribe { n -> w(n)}
//                .subscribe(bb::setTextQueueSize)
        bar.observeOn(mainThreadScheduler)

//        bar.timeInterval()

        return this
    }

    private fun w(n: Int) {
        Timber.v("n %d", n)
        Observable.fromIterable(burgers).take(n.toLong())
                .subscribe { b ->
                    Timber.d("removing 1 burger")
                    burgers.remove(b)
                    Timber.d("arraly len %d", burgers.size)
                    
                }
    }

}
