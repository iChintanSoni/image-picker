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