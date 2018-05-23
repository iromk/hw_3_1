package ru.geekbrains.android3_1

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.geekbrains.android3_1.view.MainView

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val fakeView = FakeView()

    @Test
    fun pressButtonOne_isCorrect() {
        fakeView.pressButton(MainView.Buttons.ONE)
        assertEquals("1", fakeView.text1)
        fakeView.pressButton(MainView.Buttons.ONE)
        assertEquals("2", fakeView.text1)
        assertEquals(fakeView.NO_TEXT, fakeView.text2)
        assertEquals(fakeView.NO_TEXT, fakeView.text3)
    }

    @Test
    fun pressButtonTwo_isCorrect() {
        fakeView.pressButton(MainView.Buttons.TWO)
        assertEquals("1", fakeView.text2)
        fakeView.pressButton(MainView.Buttons.TWO)
        assertEquals("2", fakeView.text2)
        assertEquals(fakeView.NO_TEXT, fakeView.text1)
        assertEquals(fakeView.NO_TEXT, fakeView.text3)
    }


    @Test
    fun pressButtonThree_isCorrect() {
        fakeView.pressButton(MainView.Buttons.THREE)
        assertEquals("1", fakeView.text3)
        fakeView.pressButton(MainView.Buttons.THREE)
        assertEquals("2", fakeView.text3)
        assertEquals(fakeView.NO_TEXT, fakeView.text1)
        assertEquals(fakeView.NO_TEXT, fakeView.text2)
    }
}

