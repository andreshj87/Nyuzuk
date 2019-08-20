package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.andres.nyuzuk.R
import kotlinx.android.synthetic.main.activity_top_articles.recyclerview_articles
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopArticlesActivity : AppCompatActivity() {
    private val viewModel: TopArticlesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_articles)
        viewModel.onInit()
        viewModel.articles.observe(this, Observer {
            recyclerview_articles.adapter = TopArticlesAdapter(it, viewModel as ArticleClickListener)
        })
    }
}
