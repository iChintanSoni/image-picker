package com.chintansoni.imagepicker_facebook.util

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView(context: Context) : AppCompatImageView(context) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}