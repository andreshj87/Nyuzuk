package com.andres.nyuzuk.data.pagination

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse

interface Paginator<T : PaginatedApiResponse> {
    companion object {
        private const val DEFAULT_PER_PAGE = 20
    }

    var page: Int
    var totalResults: Int?

    fun requestMore(): Boolean {
        var requestMore = true
        totalResults?.let { requestMore = page < it / 20 }
        return requestMore
    }

    fun processResponse(response: T) {
        page.inc()
        totalResults = response.getTotalResults()
    }

    fun getRequestConfig() = RequestConfig(page, DEFAULT_PER_PAGE)
}