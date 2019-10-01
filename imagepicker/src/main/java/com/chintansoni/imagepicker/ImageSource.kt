package com.chintansoni.imagepicker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.io.File

interface ImageSource {
    @DrawableRes
    fun getIcon(): Int

    @StringRes
    fun getTitle(): Int

    fun onClick(fragment: Fragment, file: File)
}