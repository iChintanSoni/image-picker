package com.chintansoni.imagepicker_facebook.yourphotos.albums

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("id")
    val id: String = ""
) {
    var url: String = ""
    var albumCoverApiStatus: AlbumCoverApiStatus =
        AlbumCoverApiStatus.Idle
}

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