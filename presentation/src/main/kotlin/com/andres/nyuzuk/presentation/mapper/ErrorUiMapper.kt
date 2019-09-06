package com.andres.nyuzuk.presentation.mapper

import android.content.Context
import androidx.annotation.StringRes
import com.andres.nyuzuk.R
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.presentation.entity.ErrorUi

class ErrorUiMapper(
    private val context: Context
) {
    fun map(failure: Failure): ErrorUi {
        val title = getString(R.string.error_title)
        val message = when (failure) {
            is Failure.NetworkConnection -> getString(R.string.error_network_connection)
            is Failure.ApiError -> getString(R.string.error_api)
            is Failure.EmptyResult -> getString(R.string.error_empty)
            is Failure.MappingError -> getString(R.string.error_mapping)
            is Failure.NotFoundError -> getString(R.string.error_not_found)
            is Failure.UnknownError -> getString(R.string.error_unknown)
            else -> getString(R.string.error_default)
        }
        return ErrorUi(title, message)
    }

    private fun getString(@StringRes stringResource: Int) = context.getString(stringResource)
}