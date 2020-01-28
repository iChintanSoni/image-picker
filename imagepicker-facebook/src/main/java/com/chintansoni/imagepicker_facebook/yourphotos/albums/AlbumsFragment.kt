package com.chintansoni.imagepicker_facebook.yourphotos.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker_facebook.FacebookHelper
import com.chintansoni.imagepicker_facebook.R
import kotlinx.android.synthetic.main.fragment_albums.*

class AlbumsFragment : Fragment() {

    private val albumAdapter = AlbumAdapter {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TITLE = "Albums"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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