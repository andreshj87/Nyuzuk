package com.andres.nyuzuk.presentation.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.andres.nyuzuk.R
import kotlinx.android.synthetic.main.activity_main.recyclerview_articles
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onInit()
        viewModel.articles.observe(this, Observer {
            recyclerview_articles.adapter = TopHeadlinesAdapter(it)
        })
    }
}
