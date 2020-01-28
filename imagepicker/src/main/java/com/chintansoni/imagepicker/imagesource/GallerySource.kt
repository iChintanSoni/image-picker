package com.chintansoni.imagepicker.imagesource

import android.content.Intent
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import com.chintansoni.imagepicker.exception.NoAppsFoundException
import java.io.File

internal class GallerySource :
    ImageSource() {

    companion object {
        private const val RC_PICK_PHOTO = 101
    }

    private fun openGallery(
        fragment: Fragment,
        onFailure: (Exception) -> Unit
    ) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(fragment.requireContext().packageManager) != null) {
            fragment.startActivityForResult(
                intent,
                RC_PICK_PHOTO
            )
        } else {
            onFailure.invoke(NoAppsFoundException)
        }
    }

    override fun getIcon(): Int = R.drawable.ic_gallery

    override fun getTitle(): Int = R.string.gallery

    override fun onClick(fragment: Fragment, file: File, onFailure: (Exception) -> Unit) {
        openGallery(fragment, onFailure)
    }
}