package com.chintansoni.imagepickersample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.chintansoni.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val imagePicker = ImagePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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