package com.andres.nyuzuk.presentation.di

import com.andres.nyuzuk.data.executor.JobExecutor
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.mapper.BasePublisherMapper
import com.andres.nyuzuk.domain.executor.PostExecutionThread
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import com.andres.nyuzuk.presentation.base.ArticleUiMapper
import com.andres.nyuzuk.presentation.base.BasePublisherUiMapper
import com.andres.nyuzuk.presentation.base.ErrorDialog
import com.andres.nyuzuk.presentation.base.ErrorUiMapper
import com.andres.nyuzuk.presentation.executor.UiThread
import com.andres.nyuzuk.presentation.features.detail.ArticleDetailViewModel
import com.andres.nyuzuk.presentation.features.main.MainViewModel
import com.andres.nyuzuk.presentation.features.search.ArticleSearchViewModel
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesViewModel
import com.andres.nyuzuk.presentation.tools.Navigator
import com.andres.nyuzuk.presentation.tools.imageloader.CoilImageLoader
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import com.andres.nyuzuk.data.tools.jsonserializer.JsonSerializer
import com.andres.nyuzuk.data.tools.jsonserializer.MoshiJsonSerializer
import com.andres.nyuzuk.presentation.tools.webbrowser.CustomTabsWebBrowser
import com.andres.nyuzuk.presentation.tools.webbrowser.WebBrowser
import com.squareup.moshi.Moshi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule = module(override = true) {
    single {
        JobExecutor() as ThreadExecutor
    }
    single {
        UiThread() as PostExecutionThread
    }
}

val viewModelModule: Module = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        TopArticlesViewModel(get(), get(), get())
    }

    viewModel {
        ArticleSearchViewModel(get(), get(), get())
    }

    viewModel {
        ArticleDetailViewModel()
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
        ArticleUiMapper(get())
    }

    single {
        ErrorUiMapper()
    }

    single {
        BasePublisherUiMapper()
    }
}

val toolsModule: Module = module {
    factory {
        CoilImageLoader() as ImageLoader
    }
    factory {
        Navigator()
    }
    factory {
        ErrorDialog()
    }
    factory {
        CustomTabsWebBrowser() as WebBrowser
    }
    single {
        Moshi.Builder().build()
    }
    factory {
        MoshiJsonSerializer(get()) as JsonSerializer
    }
}