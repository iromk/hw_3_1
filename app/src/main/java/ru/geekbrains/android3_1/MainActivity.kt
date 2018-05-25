package ru.geekbrains.android3_1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_main.*

import timber.log.Timber

/**
 * Created by Roman Syrchin on 5/25/18.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateInput()
    }

    private fun activateInput() {
        Timber.tag("tracer").i("start observing")
        RxTextView.textChanges(etInput).subscribe { s -> tvResult.text = s }
    }
}
