package com.chintansoni.imagepicker_facebook

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class AlbumViewHolder(private val view: View, private val onClick: (Album) -> Unit) :
    RecyclerView.ViewHolder(view) {

    val ivCoverPhoto = view.findViewById<AppCompatImageView>(R.id.iv_cover_photo)
    val tvName = view.findViewById<AppCompatTextView>(R.id.tv_album_name)
    val tvCount = view.findViewById<AppCompatTextView>(R.id.tv_album_count)

    fun setAlbum(album: Album) {
//        ivCoverPhoto.load(album.coverPic)
        tvName.setText(album.name)
        tvCount.setText(album.numberOfPhotos)
    }
}