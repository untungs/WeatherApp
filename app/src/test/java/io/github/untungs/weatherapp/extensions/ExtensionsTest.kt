package io.github.untungs.weatherapp.extensions

import org.junit.Test

import org.junit.Assert.*
import java.text.DateFormat

class ExtensionsTest {
    @Test
    fun testLongToDateString() {
        assertEquals("Jul 7, 2017", 1499397165000L.toDateString())
    }

    @Test
    fun testLongToDateStringFullFormat() {
        assertEquals("Friday, July 7, 2017", 1499397165000L.toDateString(DateFormat.FULL))
    }
}