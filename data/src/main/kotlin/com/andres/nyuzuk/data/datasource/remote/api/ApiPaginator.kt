package com.andres.nyuzuk.data.datasource.remote.api

import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import com.andres.nyuzuk.data.pagination.Paginator

class ApiPaginator(override var page: Int = Paginator.DEFAULT_PAGE, override var totalResults: Int? = null) :
    Paginator<PaginatedApiResponse>