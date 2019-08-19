package com.andres.nyuzuk.presentation.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.features.main.TopHeadlinesAdapter.ArticleViewHolder
import kotlinx.android.synthetic.main.item_article.view.text_article_title

class TopHeadlinesAdapter(val articles: List<ArticleUi>): RecyclerView.Adapter<ArticleViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_article, parent, false)
    return ArticleViewHolder(view)
  }

  override fun getItemCount(): Int {
    return articles.size
  }

  override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
    val article = articles[position]
    holder.bind(article)
  }

  class ArticleViewHolder(view: View): ViewHolder(view) {
    val titleText = view.text_article_title

    fun bind(article: ArticleUi) {
      titleText.text = article.title
    }
  }
}