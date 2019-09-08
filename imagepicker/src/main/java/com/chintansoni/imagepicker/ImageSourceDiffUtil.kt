package com.chintansoni.imagepicker

import androidx.recyclerview.widget.DiffUtil

class ImageSourceDiffUtil : DiffUtil.ItemCallback<ImageSource>() {
    override fun areItemsTheSame(oldItem: ImageSource, newItem: ImageSource): Boolean {
        return oldItem.getTitle() == newItem.getTitle()
    }

    override fun areContentsTheSame(oldItem: ImageSource, newItem: ImageSource): Boolean {
        return oldItem.getTitle() == newItem.getTitle()
    }
}