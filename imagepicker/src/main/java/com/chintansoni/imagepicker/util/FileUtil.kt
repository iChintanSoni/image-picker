package com.chintansoni.imagepicker.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.chintansoni.imagepicker.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {

    @Throws(IOException::class)
    fun createFile(context: Context): File {
        val fileName =
            SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault()).format(Date())
        val storageDir =
            File(context.cacheDir, context.getString(R.string.image_picker))
        storageDir.mkdirs()
        val file = File(storageDir, "${fileName}.png")
        file.createNewFile()
        return file
    }
}

fun File.toUri(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.imagepicker.fileprovider",
        this
    )
}

fun Uri.toFile(context: Context, file: File) {
    context.contentResolver.openInputStream(this)?.apply {
        use { input ->
            file.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }
}