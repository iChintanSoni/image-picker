package com.chintansoni.imagepicker_facebook.yourphotos.photos


import com.google.gson.annotations.SerializedName

data class UserImagesResponse(
    @SerializedName("data")
    val data: List<DataItem>?,
    @SerializedName("paging")
    val paging: Paging
)


data class ImagesItem(
    @SerializedName("width")
    val width: Int = 0,
    @SerializedName("source")
    val source: String = "",
    @SerializedName("height")
    val height: Int = 0
)


data class DataItem(
    @SerializedName("images")
    val images: List<ImagesItem>?,
    @SerializedName("id")
    val id: String = ""
)


data class Paging(
    @SerializedName("next")
    val next: String = "",
    @SerializedName("cursors")
    val cursors: Cursors
)


data class Cursors(
    @SerializedName("before")
    val before: String = "",
    @SerializedName("after")
    val after: String = ""
)


