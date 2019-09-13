package com.chintansoni.imagepicker_facebook.yourphotos.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.chintansoni.imagepicker_facebook.R

class AlbumAdapter(private val onClick: (DataItem) -> Unit) :
    ListAdapter<DataItem, AlbumViewHolder>(
        AlbumDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_album,
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.setAlbum(getItem(position))
    }
}