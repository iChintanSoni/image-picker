package com.chintansoni.imagepicker_facebook.yourphotos.albums

sealed class AlbumCoverApiStatus {
    object Idle : AlbumCoverApiStatus()
    object Loading : AlbumCoverApiStatus()
    data class Success(val albumPictureResponse: AlbumPictureResponse) : AlbumCoverApiStatus()
    data class Failure(val exception: Exception) : AlbumCoverApiStatus()
}