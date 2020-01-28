package com.chintansoni.imagepicker

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

sealed class OutputData {
    data class FileOutputData(val file: File) : OutputData()
    data class BitmapOutputData(val bitmap: Bitmap) : OutputData()
    data class UriOutputData(val uri: Uri) : OutputData()
}