package com.chintansoni.imagepicker_facebook.yourphotos

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chintansoni.imagepicker_facebook.yourphotos.albums.AlbumsFragment
import com.chintansoni.imagepicker_facebook.yourphotos.photos.PhotosFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> {
            PhotosFragment()
        }
        else -> {
            AlbumsFragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> {
            PhotosFragment.TITLE
        }
        else -> {
            AlbumsFragment.TITLE
        }
    }
}