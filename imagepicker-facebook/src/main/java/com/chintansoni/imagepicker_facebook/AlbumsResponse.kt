package com.chintansoni.imagepicker_facebook

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("id")
    val id: String = ""
)

data class Paging(
    @SerializedName("cursors")
    val cursors: Cursors
)

data class AlbumsResponse(
    @SerializedName("data")
    val data: List<DataItem>?,
    @SerializedName("paging")
    val paging: Paging
)

data class Cursors(
    @SerializedName("before")
    val before: String = "",
    @SerializedName("after")
    val after: String = ""
)

data class CoverPhoto(
    @SerializedName("created_time")
    val createdTime: String = "",
    @SerializedName("id")
    val id: String = ""
)