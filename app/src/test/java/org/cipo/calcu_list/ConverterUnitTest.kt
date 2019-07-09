package org.cipo.calcu_list

import org.cipo.calcu_list.db.encodeDoubleStringAsInt
import org.junit.Test

import org.junit.Assert.*

/**
 *
 * "" > 0
 * "0" > 0
 * "0.0" > 0
 * "00.00" > 0
 * ".00" > 0
 * "00." > 0
 * "1" > 100
 * "21474836" > 2147483600
 * "38.8" > 3880
 * "0.99" > 99
 * "9.99" > 999
 * "99.99" > 9999
 * "99.98" > 9998
 *
 */
class ConverterUnitTest {

    @Test
    fun encodeDoubleStringAsInt_Empty_isCorrect() {
        val result = encodeDoubleStringAsInt("", 2)
        assertEquals(0, result)
    }

    @Test
    fun encodeDoubleStringAsInt_0_isCorrect() {
        val result = encodeDoubleStringAsInt("0.0", 2)
        assertEquals(0, result)
    }

    @Test
    fun encodeDoubleStringAsInt_0dot0_isCorrect() {
        val result = encodeDoubleStringAsInt("0.00", 2)
        assertEquals(0, result)
    }

    @Test
    fun encodeDoubleStringAsInt_00dot00_isCorrect() {
        val result = encodeDoubleStringAsInt("00.", 2)
        assertEquals(0, result)
    }

    @Test
    fun encodeDoubleStringAsInt_1_isCorrect() {
        val result = encodeDoubleStringAsInt("1", 2)
        assertEquals(100, result)
    }

    @Test
    fun encodeDoubleStringAsInt_38_isCorrect() {
        val result = encodeDoubleStringAsInt("38.8", 2)
        assertEquals(3880, result)
    }

    @Test
    fun encodeDoubleStringAsInt_21474836_isCorrect() {
        val result = encodeDoubleStringAsInt("21474836", 2)
        assertEquals(2147483600, result)
    }

    @Test
    fun encodeDoubleStringAsInt_0dot99_isCorrect() {
        val result = encodeDoubleStringAsInt("0.99", 2)
        assertEquals(99, result)
    }

    @Test
    fun encodeDoubleStringAsInt_9dot99_isCorrect() {
        val result = encodeDoubleStringAsInt("9.99", 2)
        assertEquals(999, result)
    }

    @Test
    fun encodeDoubleStringAsInt_99dot99_isCorrect() {
        val result = encodeDoubleStringAsInt("99.99", 2)
        assertEquals(9999, result)
    }

    @Test
    fun encodeDoubleStringAsInt_99dot98_isCorrect() {
        val result = encodeDoubleStringAsInt("99.98", 2)
        assertEquals(9998, result)
    }

}
