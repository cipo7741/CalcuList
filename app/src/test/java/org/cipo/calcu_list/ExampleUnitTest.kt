package org.cipo.calcu_list

import org.junit.Test

import org.junit.Assert.*
import java.lang.Double

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun parseDouble_isCorrect() {
        val result = Double.parseDouble("33.8")
        assertEquals(33.8, result, 0.001)
    }
}
