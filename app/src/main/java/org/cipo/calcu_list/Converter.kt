package org.cipo.calcu_list

import java.math.RoundingMode
import java.text.DecimalFormat


fun encodeDoubleAsInt(entry: Double, precision: Int): Int {
    val times = Math.pow(10.0, precision.toDouble())
    return (entry * times).toInt()
}

fun encodeDoubleStringAsInt(entry: String, precision: Int): Int {
    return encodeDoubleAsInt(entry.toDouble(), precision)
}

fun decodeIntAsDouble(entry: Int, precision: Int): Double {
    val times = Math.pow(10.0, precision.toDouble())
    return entry.toDouble() / times
}

fun decodeIntAsString(entry: Int, precision: Int): String {
    val df = DecimalFormat("#.00")
    df.roundingMode = RoundingMode.HALF_EVEN
    val result = df.format(decodeIntAsDouble(entry, precision))
    return result
}

