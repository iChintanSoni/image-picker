package com.chintansoni.imagepicker_facebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_facebook_album.*

class FacebookAlbumActivity : AppCompatActivity() {

    private val albumAdapter = AlbumAdapter {
        Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_album)
        setSupportActionBar(toolbar)

        rv_facebook_album.adapter = albumAdapter
        fetchAlbums()
    }

    private fun fetchAlbums() {
        FacebookHelper.getAlbums {
            when (it) {
                is AlbumApiStatus.Loading -> {

                }
                is AlbumApiStatus.Success -> {
                    albumAdapter.submitList(it.albumsResponse.data)
                }
                is AlbumApiStatus.Failure -> {
                }
            }
        }
    }
}