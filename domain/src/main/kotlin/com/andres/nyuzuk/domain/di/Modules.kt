package com.andres.nyuzuk.domain.di

import com.andres.nyuzuk.domain.repository.ArticleRepository
import com.andres.nyuzuk.domain.usecase.GetTopArticles
import com.andres.nyuzuk.domain.usecase.SearchArticles
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    factory {
        GetTopArticles(get())
    }

    factory {
        SearchArticles(get())
    }
}

val repositoryModule: Module = module {
    single {
        ArticleRepository(get())
    }
}