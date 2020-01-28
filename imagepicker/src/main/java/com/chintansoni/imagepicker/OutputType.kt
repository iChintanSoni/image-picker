package com.chintansoni.imagepicker

sealed class OutputType {
    object FileOutputType : OutputType()
    object BitmapOutputType : OutputType()
    object UriOutputType : OutputType()
}