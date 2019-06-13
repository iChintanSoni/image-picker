package com.chintansoni.imagepicker

import android.os.Bundle
import androidx.core.os.bundleOf

data class Configuration(
    var source: Source = Source.Gallery,
    var target: Target = Target.FileTarget,
    val rationaleText: String = "This app needs permission to read external storage."
) {
    fun toBundle(): Bundle {
        return bundleOf(
            "source" to source.toString(),
            "target" to target.toString(),
            "rationaleText" to rationaleText
        )
    }

    companion object {
        fun fromBundle(bundle: Bundle): Configuration {
            val source = Source.from(bundle.getString("source") ?: "")
            val target = Target.from(bundle.getString("target") ?: "")
            val rationaleText = bundle.getString("rationaleText") ?: ""
            return Configuration(source, target, rationaleText)
        }
    }
}