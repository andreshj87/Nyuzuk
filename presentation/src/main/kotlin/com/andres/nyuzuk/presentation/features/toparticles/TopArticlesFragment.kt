package com.andres.nyuzuk.presentation.features.toparticles

import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.BaseFragment
import com.andres.nyuzuk.presentation.base.ErrorDialog
import com.andres.nyuzuk.presentation.extension.getColor
import com.andres.nyuzuk.presentation.extension.setVisibility
import com.andres.nyuzuk.presentation.tools.EndlessScrollListener
import com.andres.nyuzuk.presentation.tools.Navigator
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.fragment_top_articles.recyclerview_top_articles
import kotlinx.android.synthetic.main.fragment_top_articles.view_swipe_to_refresh
import kotlinx.android.synthetic.main.view_empty.layout_empty
import org.koin.android.ext.android.inject

class TopArticlesFragment : BaseFragment<TopArticlesViewState, TopArticlesViewModel>(TopArticlesViewModel::class) {
    private val imageLoader: ImageLoader by inject()
    private val errorDialog: ErrorDialog by inject()
    private val navigator: Navigator by inject()
    private var topArticlesAdapter: TopArticlesAdapter? = null

    companion object {
        fun newInstance() = TopArticlesFragment()
    }

    override fun getLayoutResource() = R.layout.fragment_top_articles

    override fun render(viewState: TopArticlesViewState) {
        if (viewState.articleUiToNavigate != null && context != null) {
            navigator.navigateToDetail(context!!, viewState.articleUiToNavigate)
        }
        view_swipe_to_refresh.isRefreshing = viewState.isLoading
        layout_empty.setVisibility(viewState.isEmpty)
        if (viewState.isError && viewState.errorUi != null && context != null) {
            errorDialog.show(context!!, viewState.errorUi) { viewModel.onErrorDialogDismiss() }
        }
        topArticlesAdapter?.apply {
            if (viewState.topArticlesUi.isEmpty()) {
                clear()
            } else {
                update(viewState.topArticlesUi)
            }
        }
    }

    override fun setupUi() {
        recyclerview_top_articles?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            addOnScrollListener(object : EndlessScrollListener(linearLayoutManager) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    viewModel.onLoadMore()
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            })
            topArticlesAdapter = TopArticlesAdapter(mutableListOf(), viewModel as ArticleClickListener, imageLoader)
            adapter = topArticlesAdapter
        }
        view_swipe_to_refresh?.apply {
            setColorSchemeColors(getColor(R.color.colorAccent), getColor(R.color.colorPrimary))
            setOnRefreshListener { viewModel.onRefresh() }
        }
    }
}
