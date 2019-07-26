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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

/*
 * Created by Birju Vachhani on 26 July 2019
 * Copyright © 2019 image-picker. All rights reserved.
 */

class ImageOutput internal constructor(context: Context, private val file: File) {

    private val fileUri: Uri by lazy {
        FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
    }

    fun getUri(): Uri = fileUri

    fun getBitmap(): Bitmap = BitmapFactory.decodeFile(file.absolutePath)

    fun getFile(): File {
        return file
    }
}