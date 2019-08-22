package com.andres.nyuzuk.data.pagination

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import kotlin.math.ceil

interface Paginator<T : PaginatedApiResponse> {
    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PER_PAGE = 20
    }

    var page: Int
    var totalResults: Int?

    fun invalidate() {
        page = DEFAULT_PAGE
    }

    fun requestMore(): Boolean {
        return totalResults?.let {
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