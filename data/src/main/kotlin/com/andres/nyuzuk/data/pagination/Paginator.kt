package com.andres.nyuzuk.data.pagination

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import kotlin.math.ceil

interface Paginator<T : PaginatedApiResponse> {
    companion object {
        const val DEFAULT_PAGE = 1
        private const val DEFAULT_PER_PAGE = 20
    }

    var page: Int
    var totalResults: Int?

    fun requestMore(invalidating: Boolean): Boolean {
        return totalResults?.let {
            if (invalidating) { page = DEFAULT_PAGE }
            page.toDouble() <= ceil(it.toDouble() / DEFAULT_PER_PAGE.toDouble())
        } ?: true
    }

    fun processResponse(response: T) {
        page++
        totalResults = response.getTotalResults()
    }

    fun getRequestConfig(): RequestConfig {
        return RequestConfig(page, DEFAULT_PER_PAGE)
    }
}