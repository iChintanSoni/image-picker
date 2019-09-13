package com.chintansoni.imagepicker_facebook.yourphotos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chintansoni.imagepicker_facebook.R
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager
        )
        tabLayout.setupWithViewPager(viewPager)
    }
}