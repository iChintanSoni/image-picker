package com.chintansoni.imagepicker_facebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class AlbumAdapter(private val onClick: (Album) -> Unit) :
    ListAdapter<Album, AlbumViewHolder>(AlbumDiffUtil()) {
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