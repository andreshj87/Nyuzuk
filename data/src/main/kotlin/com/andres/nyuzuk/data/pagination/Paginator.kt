package com.andres.nyuzuk.data.pagination

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import kotlin.math.ceil

interface Paginator<T : PaginatedApiResponse> {
    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 20
    }

    var page: Int
    var totalResults: Int?

    fun requestMore(invalidate: Boolean): Boolean {
        return totalResults?.let {
            if (invalidate) { page = DEFAULT_PAGE }
            page.toDouble() <= calculateLastPage(it)
        } ?: true
    }

    fun processResponse(response: T) {
        page++
        totalResults = response.getTotalResults()
    }

    fun getRequestConfig(): RequestConfig {
        return RequestConfig(page, DEFAULT_PER_PAGE)
    }

    private fun calculateLastPage(totalResults: Int): Double {
        return ceil(totalResults.toDouble() / DEFAULT_PER_PAGE.toDouble())
    }
}