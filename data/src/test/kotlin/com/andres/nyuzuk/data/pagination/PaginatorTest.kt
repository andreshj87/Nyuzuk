package com.andres.nyuzuk.data.pagination

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.datasource.remote.api.ApiPaginator
import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class PaginatorTest: UnitTest() {
    private val SOME_INVALIDATE = true
    private val SOME_TOTAL_RESULTS = 80
    private val SOME_PAGE = 4

    @Mock private lateinit var paginatedApiPaginatorMock: PaginatedApiResponse

    private lateinit var paginator: Paginator<PaginatedApiResponse>

    override fun onSetup() {
        paginator = ApiPaginator()
    }

    @Test
    fun `should request more when null totalResults`() {
        paginator.totalResults = null

        val requestMore = paginator.requestMore(SOME_INVALIDATE)

        assertThat(requestMore).isTrue()
    }

    @Test
    fun `should request more when current page is less than last page`() {
        paginator.page = 4
        paginator.totalResults = 100

        val requestMore = paginator.requestMore(false)

        assertThat(requestMore).isTrue()
    }

    @Test
    fun `should request more when invalidating even though current page was already more than last page`() {
        paginator.page = 8
        paginator.totalResults = 100

        val requestMore = paginator.requestMore(true)

        assertThat(requestMore).isTrue()
    }

    @Test
    fun `should not request more when page is over lastPage`() {
        paginator.page = 8
        paginator.totalResults = 100

        val requestMore = paginator.requestMore(false)

        assertThat(requestMore).isFalse()
    }

    @Test
    fun `should increase page when processing response`() {
        paginator.page = 4

        paginator.processResponse(paginatedApiPaginatorMock)

        assertThat(paginator.page).isEqualTo(5)
    }

    @Test
    fun `should set totalResults when processing response`() {
        `when`(paginatedApiPaginatorMock.getTotalResults()).thenReturn(SOME_TOTAL_RESULTS)
        paginator.totalResults = SOME_TOTAL_RESULTS - 1

        paginator.processResponse(paginatedApiPaginatorMock)

        assertThat(paginator.totalResults).isEqualTo(SOME_TOTAL_RESULTS)
    }

    @Test
    fun `should get requestConfig with current page and default per page`() {
        paginator.page = SOME_PAGE

        val requestConfig = paginator.getRequestConfig()

        assertThat(requestConfig).isNotNull()
        assertThat(requestConfig).isInstanceOf(RequestConfig::class.java)
        assertThat(requestConfig.page).isEqualTo(SOME_PAGE)
        assertThat(requestConfig.perPage).isEqualTo(Paginator.DEFAULT_PER_PAGE)
    }
}