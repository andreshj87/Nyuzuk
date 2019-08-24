package com.andres.nyuzuk.presentation.base

import com.andres.nyuzuk.domain.Failure

class ErrorUiMapper {
    fun map(failure: Failure): ErrorUi {
        val title = "Error"
        val message = when (failure) {
            is Failure.NetworkConnection -> "Connectivity problems"
            is Failure.ApiError -> "Api error"
            is Failure.EmptyResult -> "No results found"
            is Failure.MappingError -> "Error while mapping"
            is Failure.NotFoundError -> "404 - not found"
            is Failure.UnknownError -> "Unknown error"
            else -> "Something really bad happened"
        }
        return ErrorUi(title, message)
    }
}