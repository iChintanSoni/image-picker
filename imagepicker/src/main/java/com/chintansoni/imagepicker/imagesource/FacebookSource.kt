package com.chintansoni.imagepicker.imagesource

import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import java.io.File

class FacebookSource : ImageSource {

    override fun getIcon(): Int = R.drawable.ic_facebook

    override fun getTitle(): Int = R.string.facebook

    override fun onClick(fragment: Fragment, file: File) {

    }
}