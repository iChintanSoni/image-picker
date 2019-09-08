package com.chintansoni.imagepicker.imagesource

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import com.chintansoni.imagepicker.util.toUri
import java.io.File

class CameraSource :
    ImageSource {

    companion object {
        private const val RC_TAKE_PHOTO = 201
    }

    private fun openCamera(fragment: Fragment, file: File) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(fragment.requireContext().packageManager)?.also {
                // Continue only if the File was successfully created
                val mPhotoUri = file.toUri(fragment.requireContext())
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
                fragment.startActivityForResult(
                    takePictureIntent,
                    RC_TAKE_PHOTO
                )
            }
        }
    }

    override fun getIcon(): Int = R.drawable.ic_camera

    override fun getTitle(): Int = R.string.camera

    override fun onClick(fragment: Fragment, file: File) {
        openCamera(fragment, file)
    }
}