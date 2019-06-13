package com.chintansoni.imagepicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chintansoni.imagepicker.SelectImageBottomSheetDialogFragment.Companion.TAG

class ImagePicker {

    fun getImage(fragmentActivity: FragmentActivity, configuration: Configuration, function: (Result) -> Unit) {
        SelectImageBottomSheetDialogFragment.newInstance(configuration).apply {
            setListener(function)
        }.show(fragmentActivity.supportFragmentManager, TAG)
    }

    fun getImage(fragment: Fragment, configuration: Configuration, function: (Result) -> Unit) {
        SelectImageBottomSheetDialogFragment.newInstance(configuration).apply {
            setListener(function)
        }.show(fragment.childFragmentManager, TAG)
    }
}