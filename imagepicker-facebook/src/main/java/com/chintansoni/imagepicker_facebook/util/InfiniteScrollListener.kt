package com.chintansoni.imagepicker_facebook.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val defaultThreshold = 2

abstract class InfiniteScrollListener :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        when (recyclerView.layoutManager) {
            is LinearLayoutManager -> {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - defaultThreshold) {
                    fetchNext()
                }
            }
            is GridLayoutManager -> {
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() >= gridLayoutManager.itemCount - (gridLayoutManager.spanCount * defaultThreshold)) {
                    fetchNext()
                }
            }
        }
    }

    abstract fun fetchNext()
}