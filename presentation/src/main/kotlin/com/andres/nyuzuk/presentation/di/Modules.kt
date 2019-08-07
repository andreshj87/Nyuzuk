package com.andres.nyuzuk.presentation.di

import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.mapper.PublisherMapper
import com.andres.nyuzuk.presentation.features.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {
    // TODO add app-level dependencies here
}

val viewModelModule: Module = module {
    viewModel {
        MainViewModel(get())
    }
}

val mapperModule: Module = module {
    single {
        PublisherMapper()
    }

    single {
        ArticleMapper(get())
    }
}