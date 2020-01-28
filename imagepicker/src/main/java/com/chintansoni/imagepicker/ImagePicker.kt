/*
 * Copyright 2019 Chintan Soni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chintansoni.imagepicker

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.io.File

class ImagePicker(func: Configuration.() -> Unit = {}) {

    private val configuration = Configuration()
    private val onSuccessMutableLiveData = MutableLiveData<OutputData>()
    private val onFailureMutableLiveData = MutableLiveData<Exception>()
    private var onFailure: (Exception) -> Unit = {}

    init {
        configuration.apply(func)
    }

    fun getFile(
        fragmentActivity: FragmentActivity,
        onSuccess: (File) -> Unit
    ): ImagePicker {
        showImagePicker(fragmentActivity.supportFragmentManager, OutputType.FileOutputType)
        observeLiveData(fragmentActivity, onFileSuccess = onSuccess)
        return this
    }

    fun getUri(
        fragmentActivity: FragmentActivity,
        onSuccess: (Uri) -> Unit
    ): ImagePicker {
        showImagePicker(fragmentActivity.supportFragmentManager, OutputType.UriOutputType)
        observeLiveData(fragmentActivity, onUriSuccess = onSuccess)
        return this
    }

    fun getBitmap(
        fragmentActivity: FragmentActivity,
        onSuccess: (Bitmap) -> Unit
    ): ImagePicker {
        showImagePicker(fragmentActivity.supportFragmentManager, OutputType.BitmapOutputType)
        observeLiveData(fragmentActivity, onBitmapSuccess = onSuccess)
        return this
    }

    private fun showImagePicker(fragmentManager: FragmentManager, outputType: OutputType) {
        val imagePickerBottomSheet = ImagePickerBottomSheet().apply {
            setOutputFormat(outputType)
            setSuccessFailureLiveData(
                onSuccessMutableLiveData,
                onFailureMutableLiveData
            )
            setConfiguration(configuration)
        }
        if (!isPickerShowing(imagePickerBottomSheet)) {
            imagePickerBottomSheet.show(fragmentManager, ImagePickerBottomSheet.TAG)
        }
    }

    private fun isPickerShowing(imagePickerBottomSheet: ImagePickerBottomSheet): Boolean {
        return imagePickerBottomSheet.dialog != null
                && imagePickerBottomSheet.dialog.isShowing
                && !imagePickerBottomSheet.isRemoving
    }

    private fun observeLiveData(
        lifecycleOwner: LifecycleOwner,
        onFileSuccess: (File) -> Unit = {},
        onBitmapSuccess: (Bitmap) -> Unit = {},
        onUriSuccess: (Uri) -> Unit = {}
    ) {
        onSuccessMutableLiveData.observe(lifecycleOwner, Observer {
            when (it) {
                is OutputData.FileOutputData -> {
                    onFileSuccess.invoke(it.file)
                }
                is OutputData.UriOutputData -> {
                    onUriSuccess.invoke(it.uri)
                }
                is OutputData.BitmapOutputData -> {
                    onBitmapSuccess.invoke(it.bitmap)
                }
            }
        })
        onFailureMutableLiveData.observe(lifecycleOwner, Observer {
            onFailure.invoke(it)
        })
    }

    fun onFailure(func: (Exception) -> Unit) {
        onFailure = func
    }
}