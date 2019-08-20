package com.andres.nyuzuk.presentation.di

import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.mapper.BasePublisherMapper
import com.andres.nyuzuk.presentation.base.ImageLoader
import com.andres.nyuzuk.presentation.base.PicassoImageLoader
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUiMapper
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {
    // TODO add app-level dependencies here
}

val viewModelModule: Module = module {
    viewModel {
        TopArticlesViewModel(get(), get())
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
}

val toolsModule: Module = module {
    factory {
        PicassoImageLoader() as ImageLoader
    }
}