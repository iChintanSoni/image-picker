package com.chintansoni.imagepickersample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chintansoni.imagepicker.Configuration
import com.chintansoni.imagepicker.ImagePicker
import com.chintansoni.imagepicker.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val configuration = Configuration()
    private val imagePicker = ImagePicker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onImageClick(view: View) {
        imagePicker.getImageUri(this, configuration) {
            when (it) {
                is Result.Success -> {
                    processResult(it.data)
                }
                is Result.Failure -> {
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun processResult(uri: Uri) {
        imageView.setImageURI(uri)
    }
}
