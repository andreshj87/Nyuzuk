package com.andres.nyuzuk.presentation.dummyfactory

import com.andres.nyuzuk.presentation.entity.ErrorUi

class ErrorDummyFactory {
    companion object {
        private const val SOME_TITLE = "Critical error"
        private const val SOME_MESSAGE = "Something really bad happened."

        fun createErrorUi() = ErrorUi(SOME_TITLE, SOME_MESSAGE)
    }
}