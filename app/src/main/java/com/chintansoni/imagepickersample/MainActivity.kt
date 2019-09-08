package com.chintansoni.imagepickersample

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.chintansoni.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    private val imagePicker = ImagePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateFBHash()
    }

    private fun generateFBHash() {
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

    }

    fun onImageAsFileClick(view: View) {
        imagePicker.getFile(this) {
            imageView.load(it)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun onImageAsUriClick(view: View) {
        imagePicker.getUri(this) {
            imageView.load(it)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun onImageAsBitmapClick(view: View) {
        imagePicker.getBitmap(this) {
            imageView.load(it)
        }.onFailure {
            it.printStackTrace()
        }
    }
}