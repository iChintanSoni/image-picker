package com.chintansoni.imagepicker_facebook.yourphotos.albums

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.chintansoni.imagepicker_facebook.FacebookHelper
import com.chintansoni.imagepicker_facebook.R

class AlbumViewHolder(private val view: View, private val onClick: (DataItem) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val ivCoverPhoto = view.findViewById<AppCompatImageView>(R.id.iv_cover_photo)
    private val tvName = view.findViewById<AppCompatTextView>(R.id.tv_album_name)
    private val tvCount = view.findViewById<AppCompatTextView>(R.id.tv_album_count)

    fun setAlbum(album: DataItem) {
        view.setOnClickListener {
            onClick(album)
        }
        tvName.text = album.name
        tvCount.text = "${album.count} photos"
        if (album.url.isNotEmpty()) {
            ivCoverPhoto?.load(album.url)
        } else {
            FacebookHelper.getAlbumCoverPhoto(album) {
                ivCoverPhoto?.load(album.url)
            }
        }
    }
}