package com.chintansoni.imagepicker.imagesource

import android.content.Intent
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import java.io.File

internal class GallerySource :
    ImageSource {

    companion object {
        private const val RC_PICK_PHOTO = 101
    }

    private fun openGallery(fragment: Fragment) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(fragment.requireContext().packageManager) != null) {
            fragment.startActivityForResult(
                intent,
                RC_PICK_PHOTO
            )
        } else {
            // TODO: pass error to consumer creating an exception "NoAppsFoundException"
//            imageTask.onFailureFunc.invoke(Throwable("No apps found that can handle this action"))
        }
    }

    override fun getIcon(): Int = R.drawable.ic_gallery

    override fun getTitle(): Int = R.string.gallery

    override fun onClick(fragment: Fragment, file: File) {
        openGallery(fragment)
    }
}