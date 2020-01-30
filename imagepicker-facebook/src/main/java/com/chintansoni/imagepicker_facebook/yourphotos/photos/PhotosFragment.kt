package com.chintansoni.imagepicker_facebook.yourphotos.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker_facebook.FacebookHelper
import com.chintansoni.imagepicker_facebook.R
import com.chintansoni.imagepicker_facebook.util.GridLayoutSpacingItemDecoration
import com.chintansoni.imagepicker_facebook.util.PXDPUtil
import com.chintansoni.imagepicker_facebook.util.setInfiniteScroll
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {
    companion object {
        const val TITLE = "Photos"
    }

    private val photosAdapter = PhotosAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = 3
        val spacing = PXDPUtil.convertDpToPixel(16, requireContext())
        val includeEdge = true
        rv_images.apply {
            addItemDecoration(
                GridLayoutSpacingItemDecoration(
                    spanCount,
                    spacing,
                    includeEdge
                )
            )
            adapter = photosAdapter
            setInfiniteScroll {
                FacebookHelper.pageUserImages {

                }
            }
        }
        getImages()
    }

    private fun getImages() {
        FacebookHelper.getUserImages {
            photosAdapter.submitList(it.data)
        }
    }
}