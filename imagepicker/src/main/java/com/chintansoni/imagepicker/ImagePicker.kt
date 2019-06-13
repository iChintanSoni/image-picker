package com.chintansoni.imagepicker

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.chintansoni.imagepicker.SelectImageBottomSheetDialogFragment.Companion.TAG
import java.io.File

class ImagePicker {

    fun getImageFile(
        fragmentActivity: FragmentActivity,
        configuration: Configuration,
        function: (Result<File>) -> Unit
    ) {
        fragmentActivity.supportFragmentManager
    }

    fun getImageUri(fragmentActivity: FragmentActivity, configuration: Configuration, function: (Result<Uri>) -> Unit) {
        SelectImageBottomSheetDialogFragment.newInstance(configuration).apply {
            setImageListener(function)
        }.show(fragmentActivity.supportFragmentManager, TAG)
    }

    fun getImageBitmap(
        fragmentActivity: FragmentActivity,
        configuration: Configuration,
        function: (Result<Bitmap>) -> Unit
    ) {

    }

    fun getImageBase64(
        fragmentActivity: FragmentActivity,
        configuration: Configuration,
        function: (Result<String>) -> Unit
    ) {

    }
}