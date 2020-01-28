package com.chintansoni.imagepicker

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.io.File

abstract class ImageSource {
    @DrawableRes
    abstract fun getIcon(): Int

    @StringRes
    abstract fun getTitle(): Int

    abstract fun onClick(fragment: Fragment, file: File, onFailure: (Exception) -> Unit = {})

    open fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {}
}