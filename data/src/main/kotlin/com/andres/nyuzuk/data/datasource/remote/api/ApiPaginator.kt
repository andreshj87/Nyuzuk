package com.andres.nyuzuk.data.datasource.remote.api

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import com.andres.nyuzuk.data.pagination.Paginator

class ApiPaginator(override var page: Int = 1, override var totalResults: Int? = null) :
    Paginator<PaginatedApiResponse>