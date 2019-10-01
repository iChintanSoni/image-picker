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

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.chintansoni.imagepicker.exception.UnexpectedOutputTypeException
import com.chintansoni.imagepicker.imagesource.CameraSource
import com.chintansoni.imagepicker.imagesource.FacebookSource
import com.chintansoni.imagepicker.imagesource.GallerySource
import com.chintansoni.imagepicker.util.FileUtil.createFile
import com.chintansoni.imagepicker.util.toFile
import com.chintansoni.imagepicker.util.toUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_image_picker.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

internal class ImagePickerBottomSheet : BottomSheetDialogFragment(),
    EasyPermissions.PermissionCallbacks {

    private lateinit var onFailureMutableLiveData: MutableLiveData<Exception>
    private lateinit var onSuccessMutableLiveData: MutableLiveData<OutputData>

    private lateinit var configuration: Configuration

    private var imageSource: ImageSource? = null
    private val imageSourceList = arrayListOf<ImageSource>()
    private lateinit var file: File
    private lateinit var outputType: OutputType

    private val asyncTask = @SuppressLint("StaticFieldLeak")
    object : AsyncTask<OutputType, Void, OutputData>() {
        override fun doInBackground(vararg outputType: OutputType?): OutputData? {

            // Respond Accordingly
            return when (outputType[0]) {
                is OutputType.FileOutputType -> OutputData.FileOutputData(file)
                is OutputType.BitmapOutputType -> OutputData.BitmapOutputData(
                    BitmapFactory.decodeFile(
                        file.absolutePath
                    )
                )
                is OutputType.UriOutputType -> OutputData.UriOutputData(file.toUri(requireContext()))
                else -> null
            }
        }

        override fun onPostExecute(result: OutputData?) {
            super.onPostExecute(result)
            dismiss()
            if (result == null) {
                onFailureMutableLiveData.postValue(UnexpectedOutputTypeException)
            } else {
                onSuccessMutableLiveData.postValue(result)
            }
        }
    }

    companion object {
        private const val RC_EXTERNAL_STORAGE = 100
        private const val BUNDLE_KEY_CONFIG = "config"
        private const val SETTINGS_ACTIVITY_REQUEST_CODE = 659
        internal const val TAG = "ImagePickerBottomSheet"
        private val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        fun newInstance(configuration: Configuration): ImagePickerBottomSheet =
            ImagePickerBottomSheet().apply {
                arguments = Bundle().apply { putParcelable(BUNDLE_KEY_CONFIG, configuration) }
            }
    }

    fun setOutputFormat(outputType: OutputType) {
        this.outputType = outputType
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuration = arguments?.getParcelable(BUNDLE_KEY_CONFIG) ?: Configuration()
        addImageSource()
    }

    private fun addImageSource() {
        // Add Camera option
        imageSourceList.add(CameraSource())
        // Add Gallery option
        imageSourceList.add(GallerySource())
        // Add Facebook option
        try {
            Class.forName("com.chintansoni.imagepicker_facebook.FacebookLoginActivity")
            imageSourceList.add(FacebookSource())
        } catch (exception: ClassNotFoundException) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_image_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageSourceAdapter = ImageSourceAdapter {
            imageSource = it
            handleImageSourceClick(imageSource)
        }
        rv_image_picker.adapter = imageSourceAdapter
        imageSourceAdapter.submitList(imageSourceList)
    }

    private fun handleImageSourceClick(imageSource: ImageSource?) {
        println("ImageSource: ${imageSource!!::class.java.simpleName}")
        file = createFile(requireContext())
        when (imageSource) {
            is GallerySource -> {
                invokePermissionModel(this)
            }
            is CameraSource -> {
                imageSource.onClick(this, file)
            }
            is FacebookSource -> {
                imageSource.onClick(this, file)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            handleImageSourceClick(imageSource)
        } else {
            // Save file as common output
            when (imageSource) {
                is CameraSource -> {

                }
                is GallerySource -> {
                    data?.data?.toFile(requireContext(), file)
                }
                is FacebookSource -> {

                }
            }

            dispatchResult()
        }
    }

    private fun dispatchResult() {
        asyncTask.execute(outputType)
    }

    private fun invokePermissionModel(fragment: Fragment) {
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            imageSource?.onClick(fragment, file)
        } else {
            EasyPermissions.requestPermissions(
                fragment,
                configuration.rationaleText,
                RC_EXTERNAL_STORAGE,
                *perms
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showPermanentlyDeniedDialog()
        } else {
            dismiss()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        imageSource?.onClick(this, file)
    }

    /**
     * Opens app settings screen
     */
    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivityForResult(intent, SETTINGS_ACTIVITY_REQUEST_CODE)
    }

    /**
     * Shows permission permanently blocked dialog
     */
    private fun showPermanentlyDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(configuration.blockedTitle)
            .setMessage(configuration.blockedText)
            .setPositiveButton(R.string.open_settings) { dialog, _ ->
                openSettings()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                dismiss()
            }
            .setCancelable(false)
            .create()
            .takeIf { !requireActivity().isFinishing }?.show()
    }

    fun setSuccessFailureLiveData(
        onSuccessMutableLiveData: MutableLiveData<OutputData>,
        onFailureMutableLiveData: MutableLiveData<Exception>
    ) {
        this.onSuccessMutableLiveData = onSuccessMutableLiveData
        this.onFailureMutableLiveData = onFailureMutableLiveData
    }
}