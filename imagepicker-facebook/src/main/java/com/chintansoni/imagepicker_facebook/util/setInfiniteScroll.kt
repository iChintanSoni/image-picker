package com.chintansoni.imagepicker_facebook.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setInfiniteScroll(func: () -> Unit) {
    addOnScrollListener(object : InfiniteScrollListener() {
        override fun fetchNext() {
            func.invoke()
        }
    })
}