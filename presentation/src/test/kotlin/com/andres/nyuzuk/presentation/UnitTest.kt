package com.andres.nyuzuk.presentation

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