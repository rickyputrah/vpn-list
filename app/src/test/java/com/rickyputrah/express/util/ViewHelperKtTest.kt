package com.rickyputrah.express.util

import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewHelperKtTest {

    @Test
    fun `should return Visible when call toVisibility with true`() {

        val result = true.toVisibility()

        assertEquals(View.VISIBLE, result)
    }

    @Test
    fun `should return GONE when call toVisibility with false`() {

        val result = false.toVisibility()

        assertEquals(View.GONE, result)
    }
}