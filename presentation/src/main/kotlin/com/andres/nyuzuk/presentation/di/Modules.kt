package com.andres.nyuzuk.presentation.di

import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.mapper.BasePublisherMapper
import com.andres.nyuzuk.presentation.base.ErrorDialog
import com.andres.nyuzuk.presentation.base.ErrorUiMapper
import com.andres.nyuzuk.presentation.features.main.MainViewModel
import com.andres.nyuzuk.presentation.features.search.SearchArticlesViewModel
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUiMapper
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesViewModel
import com.andres.nyuzuk.presentation.tools.Navigator
import com.andres.nyuzuk.presentation.tools.imageloader.CoilImageLoader
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {
    // Add app-level dependencies here
}

val viewModelModule: Module = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        TopArticlesViewModel(get(), get(), get())
    }

    viewModel {
        SearchArticlesViewModel(get(), get(), get())
    }
}

val mapperModule: Module = module {
    single {
        BasePublisherMapper()
    }

    single {
        ArticleMapper(get())
    }

    single {
        ArticleUiMapper()
    }

    single {
        ErrorUiMapper()
    }
}

val toolsModule: Module = module {
    factory {
        CoilImageLoader() as ImageLoader
    }
    single {
        Navigator()
    }
    factory {
        ErrorDialog()
    }
}