package com.edenh.newsclient.view.adapters

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleDateTest {

    @Test
    fun date_isCorrect() {
        val dates = mapOf(
            "2020-04-10T14:10:35Z" to "10/04/2020 17:10",
            "2020-04-10T14:07:28Z" to "10/04/2020 17:07",
            "" to "",
            null to ""
        )

        //Dummy adapter just to check the date function
        val adapter = ArticlesAdapter(listOf())

        for (expectedDate in dates) {
            assertEquals(adapter.convertDatePattern(expectedDate.key), expectedDate.value)
        }
    }
}