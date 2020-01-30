package com.chintansoni.imagepicker_facebook.yourphotos.albums

sealed class AlbumApiStatus {
    object Loading : AlbumApiStatus()
    data class Success(val albumsResponse: AlbumsResponse) : AlbumApiStatus()
    data class Failure(val exception: Exception) : AlbumApiStatus()
}