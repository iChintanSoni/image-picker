package com.chintansoni.imagepicker

import android.os.Bundle
import androidx.core.os.bundleOf

data class Configuration(
    var target: Target = Target.UriTarget,
    val rationaleText: String = "This app needs permission to read external storage."
) {
    fun toBundle(): Bundle {
        return bundleOf(
            "target" to target.toString(),
            "rationaleText" to rationaleText
        )
    }

    companion object {
        fun fromBundle(bundle: Bundle): Configuration {
            val target = Target.from(bundle.getString("target") ?: "")
            val rationaleText = bundle.getString("rationaleText") ?: ""
            return Configuration(target, rationaleText)
        }
    }
}