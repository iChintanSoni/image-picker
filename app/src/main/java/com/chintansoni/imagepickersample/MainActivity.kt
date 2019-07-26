package com.chintansoni.imagepickersample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chintansoni.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val imagePicker = ImagePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onImageAsFileClick(view: View) {
        imagePicker.getImage(this) {
            Log.e("MainActivity", "Received Result")
            Glide
                .with(this@MainActivity)
                .load(getFile())
                .into(imageView)
        }.onFailure {
            Log.e("MainActivity", it.message)
        }

        imagePicker.getImage(this) {

        }
    }

    fun onImageAsUriClick(view: View) {
        imagePicker.getImage(this) {
            Glide
                .with(this@MainActivity)
                .load(getUri())
                .into(imageView)
        }.onFailure {
            Log.e("MainActivity", it.message)
        }
    }

    fun onImageAsBitmapClick(view: View) {
        imagePicker.getImage(this) {
            Glide
                .with(this@MainActivity)
                .load(getBitmap())
                .into(imageView)
        }.onFailure {
            Log.e("MainActivity", it.message)
        }
    }
}
