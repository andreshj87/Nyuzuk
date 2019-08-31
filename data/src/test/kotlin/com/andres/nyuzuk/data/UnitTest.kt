package com.andres.nyuzuk.data

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class UnitTest {
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        onSetup()
    }

    abstract fun onSetup()
}