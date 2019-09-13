package com.chintansoni.imagepicker_facebook.yourphotos.photos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.chintansoni.imagepicker_facebook.R
import com.chintansoni.imagepicker_facebook.util.SquareImageView

class PhotoViewHolder(view: View, private val onClick: (DataItem) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val ivCoverPhoto = view as SquareImageView

    fun setAlbum(dataItem: DataItem) {
        ivCoverPhoto.load(dataItem.images?.lastOrNull()?.source ?: "") {
            placeholder(R.drawable.ic_image_black)
        }
    }
}