package com.chintansoni.imagepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageSourceAdapter(private val func: (ImageSource) -> Unit) :
    ListAdapter<ImageSource, ImageSourceAdapter.ImageSourceViewHolder>(ImageSourceDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSourceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_image_source, parent, false)
        return ImageSourceViewHolder(func, view)
    }

    override fun onBindViewHolder(holder: ImageSourceViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class ImageSourceViewHolder(private val func: (ImageSource) -> Unit, private val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bindData(item: ImageSource) {
            val materialButton = view as AppCompatButton
            materialButton.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getIcon(), 0, 0, 0)
            materialButton.setText(item.getTitle())
            materialButton.setOnClickListener {
                func.invoke(item)
            }
        }
    }
}