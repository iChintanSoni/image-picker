package com.chintansoni.imagepicker_facebook.yourphotos.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.chintansoni.imagepicker_facebook.R

class PhotosAdapter(private val onClick: (DataItem) -> Unit) :
    ListAdapter<DataItem, PhotoViewHolder>(
        PhotoDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_image,
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setAlbum(getItem(position))
    }
}