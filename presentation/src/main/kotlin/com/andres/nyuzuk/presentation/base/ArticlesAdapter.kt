package com.andres.nyuzuk.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class ArticlesAdapter(
    private val articlesUi: MutableList<ArticleUi>,
    private val articleClickListener: ArticleClickListener
) : ListAdapter<ArticleUi, ArticlesAdapter.ArticleViewHolder>(DiffCallback()) {
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

    fun update(articlesUi: List<ArticleUi>) {
        this.articlesUi.addAll(articlesUi)
        submitList(this.articlesUi)
    }

    fun clear() {
        this.articlesUi.clear()
        submitList(null)
    }

    abstract inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindItem(articleUi: ArticleUi)

        abstract fun getClickableView(): View

        fun bind(articleUi: ArticleUi) {
            getClickableView().setOnClickListener { articleClickListener.onArticleClick(articleUi) }
            bindItem(articleUi)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<ArticleUi>() {
    override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem == newItem
    }
}