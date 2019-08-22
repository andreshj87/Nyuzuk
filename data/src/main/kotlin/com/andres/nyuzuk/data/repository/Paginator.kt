package com.andres.nyuzuk.data.repository

class Paginator(
    private var page: Int = 1
) {
    companion object {
        private val PER_PAGE = 20
    }

    private var total: Int? = null

    fun updatePage() = page.inc()

    fun updateTotal(total: Int) {
        this.total = total
    }

    fun shouldContinue(): Boolean {
        var shouldContinue = true
        total?.let {
            shouldContinue = page < it / PER_PAGE
        }
        return shouldContinue
    }
}