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
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_image_picker.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class SelectImageBottomSheetDialogFragment : BottomSheetDialogFragment(), EasyPermissions.PermissionCallbacks {

    private var mPhotoUri: Uri? = null
    private var mListener: (Result) -> Unit = {}
    private var mConfiguration: Configuration? = null

    companion object {
        const val RC_EXTERNAL_STORAGE = 100
        const val REQUEST_TAKE_PHOTO = 101
        const val REQUEST_PICK_PHOTO = 102
        const val TAG = "SelectImageBottomSheetDialogFragment"
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        fun newInstance(configuration: Configuration): SelectImageBottomSheetDialogFragment =
            SelectImageBottomSheetDialogFragment().apply {
                arguments = configuration.toBundle()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mConfiguration = Configuration.fromBundle(it)
        }
        askReadExternalStoragePermission()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_image_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_camera.setOnClickListener {
            openCamera()
        }
        btn_gallery.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(intent, REQUEST_PICK_PHOTO)
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    mPhotoUri =
                        FileProvider.getUriForFile(requireContext(), "com.chintansoni.imagepicker.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    fun setListener(function: (Result) -> Unit) {
        this.mListener = function
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = File(requireContext().cacheDir, "cameraPics")
        storageDir.mkdirs()
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            if (!EasyPermissions.hasPermissions(requireContext(), *perms)) {
                dismiss()
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            mPhotoUri?.let {
                processResultForUri(it)
            }
            dismiss()
        } else if (requestCode == REQUEST_PICK_PHOTO && resultCode == RESULT_OK) {
            data?.data?.let {
                processResultForUri(it)
            }
            dismiss()
        }
    }

    private fun processResultForUri(uri: Uri) {
        when (mConfiguration?.target) {
            is Target.UriTarget -> {
                mListener(Result.Success(Output.UriOutput(uri)))
            }
            is Target.FileTarget -> {
                mListener(Result.Success(Output.FileOutput(getFileFromUri(uri))))
            }
            is Target.BitmapTarget -> {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                mListener(Result.Success(Output.BitmapOutput(BitmapFactory.decodeStream(inputStream))))
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File {
        val file = createImageFile()
        requireContext().contentResolver.openInputStream(uri)?.let {
            file.copyInputStreamToFile(it)
        }
        return file
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        inputStream.use { input ->
            this.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

    // [Start] Permission Model
    private fun askReadExternalStoragePermission() {
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this,
                mConfiguration?.rationaleText.toString(),
                RC_EXTERNAL_STORAGE,
                *perms
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            dismiss()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
    // [End] Permission Model
}