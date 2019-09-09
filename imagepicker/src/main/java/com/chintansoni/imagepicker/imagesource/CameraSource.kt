package com.chintansoni.imagepicker.imagesource

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import com.chintansoni.imagepicker.exception.NoAppsFoundException
import com.chintansoni.imagepicker.util.toUri
import java.io.File

class CameraSource :
    ImageSource() {

    companion object {
        private const val RC_TAKE_PHOTO = 201
    }

    private fun openCamera(
        fragment: Fragment,
        file: File,
        onFailure: (Exception) -> Unit
    ) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(fragment.requireContext().packageManager) != null) {
            val mPhotoUri = file.toUri(fragment.requireContext())
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
            fragment.startActivityForResult(
                intent,
                RC_TAKE_PHOTO
            )
        } else {
            onFailure.invoke(NoAppsFoundException)
        }
    }

    override fun getIcon(): Int = R.drawable.ic_camera

    override fun getTitle(): Int = R.string.camera

    override fun onClick(fragment: Fragment, file: File, onFailure: (Exception) -> Unit) {
        openCamera(fragment, file, onFailure)
    }
}