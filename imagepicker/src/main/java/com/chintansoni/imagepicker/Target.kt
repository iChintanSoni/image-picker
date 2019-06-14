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

sealed class Target(val type: String) {
    object FileTarget : Target("file")
    object UriTarget : Target("uri")
    object BitmapTarget : Target("bitmap")
    object Undefined : Target("undefined")

    companion object {
        fun from(type: String): Target {
            return when (type) {
                "file" -> FileTarget
                "uri" -> UriTarget
                "bitmap" -> BitmapTarget
                else -> Undefined
            }
        }
    }

    override fun toString() = type
}