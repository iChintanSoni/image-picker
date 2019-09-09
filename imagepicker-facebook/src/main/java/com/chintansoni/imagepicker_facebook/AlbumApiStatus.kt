package com.chintansoni.imagepicker_facebook

sealed class AlbumApiStatus {
    object Loading : AlbumApiStatus()
    data class Success(val albumsResponse: AlbumsResponse) : AlbumApiStatus()
    data class Failure(val exception: Exception) : AlbumApiStatus()
}