package ru.geekbrains.android3_1.hw2

import io.reactivex.Observable

/**
 * Created by Roman Syrchin on 5/28/18.
 */
class Manager {

    fun manage() {

        class Client {}
        class Food {}

        class Bar {}

        var oc: Observable<Client>
        var of: Observable<Food>

        var bar: Observable<Bar>

        bar.subscribe(oc)

    }

}
