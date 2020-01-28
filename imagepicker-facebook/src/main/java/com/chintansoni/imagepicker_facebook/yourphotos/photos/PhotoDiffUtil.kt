package com.chintansoni.imagepicker_facebook.yourphotos.photos

import androidx.recyclerview.widget.DiffUtil

class PhotoDiffUtil : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}