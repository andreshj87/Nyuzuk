package com.andres.nyuzuk.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andres.nyuzuk.presentation.features.toparticles.ArticleClickListener
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi

abstract class ArticlesAdapter(
    private val articlesUi: List<ArticleUi>,
    private val articleClickListener: ArticleClickListener
): RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    abstract fun getLayoutResource(): Int

    abstract fun getViewHolder(itemView: View): ArticleViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(getLayoutResource(), parent, false)
        return getViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articlesUi.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleUi = articlesUi[position]
        holder.bind(articleUi)
    }

    abstract inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bindItem(articleUi: ArticleUi)

        abstract fun getClickableView(): View

        fun bind(articleUi: ArticleUi) {
            getClickableView().setOnClickListener { articleClickListener.onArticleClick(articleUi) }
            bindItem(articleUi)
        }
    }
}