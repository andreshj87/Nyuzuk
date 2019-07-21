package com.andres.nyuzuk.presentation.di

import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.mapper.PublisherMapper
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {
    // TODO add app-level dependencies here
}

val mapperModule: Module = module {
    single {
        PublisherMapper()
    }

    single {
        ArticleMapper(get())
    }
}