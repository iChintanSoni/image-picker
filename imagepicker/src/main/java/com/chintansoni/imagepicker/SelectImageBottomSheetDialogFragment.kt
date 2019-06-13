package com.chintansoni.imagepicker

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import java.text.SimpleDateFormat
import java.util.*


class SelectImageBottomSheetDialogFragment : BottomSheetDialogFragment(), EasyPermissions.PermissionCallbacks {


    private var photoURI: Uri? = null

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            dismiss()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            if (!EasyPermissions.hasPermissions(requireContext(), *perms)) {
                dismiss()
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoURI?.let {
                listener(Result.Success(it))
                dismiss()
            }
        }
    }

    private var listener: (Result<Uri>) -> Unit = {}
    private var configuration: Configuration? = null

    private val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object {
        const val REQUEST_TAKE_PHOTO = 101
        const val TAG = "SelectImageBottomSheetDialogFragment"
        const val RC_EXTERNAL_STORAGE = 100
        fun newInstance(configuration: Configuration): SelectImageBottomSheetDialogFragment =
            SelectImageBottomSheetDialogFragment().apply {
                arguments = configuration.toBundle()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            configuration = Configuration.fromBundle(it)
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
        listener.invoke(Result.Failure(Throwable("Open Gallery")))
    }

    private fun openCamera() {
        dispatchTakePictureIntent()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun askReadExternalStoragePermission() {
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this,
                configuration?.rationaleText.toString(),
                RC_EXTERNAL_STORAGE,
                *perms
            )
        }
    }

    fun setImageListener(function: (Result<Uri>) -> Unit) {
        this.listener = function
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = File(requireContext().cacheDir, "cameraPics")
        storageDir.mkdirs()
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun dispatchTakePictureIntent() {
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
                    photoURI =
                        FileProvider.getUriForFile(requireContext(), "com.chintansoni.imagepicker.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
}