package com.andres.nyuzuk.domain

import com.nhaarman.mockitokotlin2.KStubbing
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing

abstract class UnitTest {
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        onSetup()
    }

    abstract fun onSetup()

    fun <T : Any, R> KStubbing<T>.onBlocking(m: suspend T.() -> R)
            : OngoingStubbing<R> {
        return runBlocking { Mockito.`when`(mock.m()) }
    }

    fun <T> mockBlocking(methodCall: T, valueToReturn: T) {
        runBlocking {
            Mockito.`when`(methodCall).thenReturn(valueToReturn)
        }
    }

    fun <T> verifyBlocking(mock: T, f: suspend T.() -> Unit) {
        val m = Mockito.verify(mock)
        runBlocking { m.f() }
    }
}