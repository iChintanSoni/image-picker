package com.chintansoni.imagepicker_facebook


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("is_silhouette")
    val isSilhouette: Boolean = false,
    @SerializedName("url")
    val url: String = ""
)


data class AlbumPictureResponse(
    @SerializedName("data")
    val data: Data
)


