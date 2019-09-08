package com.chintansoni.imagepicker_facebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_facebook_album.*

class FacebookAlbumActivity : AppCompatActivity() {

    private val albumAdapter = AlbumAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_album)
        setSupportActionBar(toolbar)

        rv_facebook_album.adapter = albumAdapter
        fetchAlbums()
    }

    private fun fetchAlbums() {
        FacebookHelper.getAlbums()
    }
}