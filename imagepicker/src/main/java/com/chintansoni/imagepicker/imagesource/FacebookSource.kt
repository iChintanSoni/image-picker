package com.chintansoni.imagepicker.imagesource

import android.content.Intent
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import com.chintansoni.imagepicker.R
import com.chintansoni.imagepicker_facebook.FacebookAlbumActivity
import com.chintansoni.imagepicker_facebook.FacebookHelper
import java.io.File

class FacebookSource : ImageSource {

    private val facebookHelper = FacebookHelper

    override fun getIcon(): Int = R.drawable.ic_facebook

    override fun getTitle(): Int = R.string.facebook

    override fun onClick(fragment: Fragment, file: File) {
        if (facebookHelper.isLoggedIn()) {
            fragment.startActivity(
                Intent(
                    fragment.requireContext(),
                    FacebookAlbumActivity::class.java
                )
            )
        } else {
            facebookHelper.login(fragment, {
                println("Access Token: ${it.accessToken}")
                fragment.startActivity(
                    Intent(
                        fragment.requireContext(),
                        FacebookAlbumActivity::class.java
                    )
                )
            }, {
                it.printStackTrace()
            })
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        facebookHelper.onActivityResult(requestCode, resultCode, data)
    }
}