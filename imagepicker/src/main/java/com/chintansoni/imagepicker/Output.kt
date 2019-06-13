package com.chintansoni.imagepicker

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

sealed class Output {
    data class FileOutput(val data: File) : Output()
    data class UriOutput(val data: Uri) : Output()
    data class BitmapOutput(val data: Bitmap) : Output()
}