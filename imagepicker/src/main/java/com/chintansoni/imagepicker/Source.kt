package com.chintansoni.imagepicker

sealed class Source(val type: String) {
    object Camera : Source("camera")
    object Gallery : Source("gallery")
    object Undefined : Source("undefined")

    companion object {
        fun from(type: String): Source {
            return when (type) {
                Camera.type -> Camera
                Gallery.type -> Gallery
                else -> Undefined
            }
        }
    }

    override fun toString() = type
}