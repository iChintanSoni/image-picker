package com.chintansoni.imagepickersample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chintansoni.imagepicker.Configuration
import com.chintansoni.imagepicker.ImagePicker
import com.chintansoni.imagepicker.Output
import com.chintansoni.imagepicker.Result
import com.chintansoni.imagepicker.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val imagePicker = ImagePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onImageAsFileClick(view: View) {
        val configuration = Configuration(target = Target.FileTarget)
        getImage(configuration)
    }

    fun onImageAsUriClick(view: View) {
        val configuration = Configuration(target = Target.UriTarget)
        getImage(configuration)
    }

    fun onImageAsBitmapClick(view: View) {
        val configuration = Configuration(target = Target.BitmapTarget)
        getImage(configuration)
    }

    private fun getImage(configuration: Configuration) {
        imagePicker.getImage(this, configuration) {
            when (it) {
                is Result.Success -> {
                    processResult(it.output)
                }
                is Result.Failure -> {
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun processResult(output: Output) {
        when (output) {
            is Output.FileOutput -> {
                Glide
                    .with(this)
                    .load(output.data)
                    .into(imageView)
            }
            is Output.UriOutput -> {
                Glide
                    .with(this)
                    .load(output.data)
                    .into(imageView)
            }
            is Output.BitmapOutput -> {
                Glide
                    .with(this)
                    .load(output.data)
                    .into(imageView)
            }
        }
    }
}
