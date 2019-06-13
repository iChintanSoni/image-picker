package com.chintansoni.imagepicker

sealed class Target(val type: String) {
    object FileTarget : Target("file")
    object UriTarget : Target("uri")
    object Base64Target : Target("base64")
    object BitmapTarget : Target("bitmap")
    object Undefined : Target("undefined")

    companion object {
        fun from(type: String): Target {
            return when (type) {
                "file" -> FileTarget
                "uri" -> UriTarget
                "base64" -> Base64Target
                "bitmap" -> BitmapTarget
                else -> Undefined
            }
        }
    }

    override fun toString() = type
}