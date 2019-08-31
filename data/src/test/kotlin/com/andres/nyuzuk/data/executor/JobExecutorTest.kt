package com.andres.nyuzuk.data.executor

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class JobExecutorTest: UnitTest() {
    private lateinit var jobExecutor: ThreadExecutor

    override fun onSetup() {
        jobExecutor = JobExecutor()
    }

    @Test
    internal fun `should get scheduler as IO CoroutineDispatcher`() {
        val scheduler = jobExecutor.getScheduler()

        assertThat(scheduler).isEqualTo(Dispatchers.IO)
    }
}