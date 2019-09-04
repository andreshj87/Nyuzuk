package com.andres.nyuzuk.presentation.executor

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.UnitTest
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class UiThreadTest: UnitTest() {
    private lateinit var uiThread: UiThread

    override fun onSetup() {
        uiThread = UiThread()
    }

    @Test
    fun `should get main dispatcher when getting scheduler`() {
        val schedulerExpected = Dispatchers.Main

        val scheduler = uiThread.getScheduler()

        assertThat(scheduler).isEqualTo(schedulerExpected)
    }
}