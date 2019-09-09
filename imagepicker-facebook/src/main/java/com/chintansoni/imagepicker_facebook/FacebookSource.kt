package com.chintansoni.imagepicker_facebook

import android.content.Intent
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker.ImageSource
import java.io.File

class FacebookSource : ImageSource() {

    override fun onClick(fragment: Fragment, file: File, onFailure: (Exception) -> Unit) {
        if (FacebookHelper.isLoggedIn()) {
            fragment.startActivity(
                Intent(
                    fragment.requireContext(),
                    FacebookAlbumActivity::class.java
                )
            )
        } else {
            FacebookHelper.login(fragment, {
                fragment.startActivity(
                    Intent(
                        fragment.requireContext(),
                        FacebookAlbumActivity::class.java
                    )
                )
            }, {
                onFailure.invoke(it)
            })
        }
    }

    override fun getIcon(): Int = R.drawable.ic_facebook

    override fun getTitle(): Int = R.string.facebook

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        FacebookHelper.onActivityResult(requestCode, resultCode, intent)
    }
}