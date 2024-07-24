package com.example.socialnetwork.utils

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.socialnetwork.ui.BaseActivity
import com.github.dhaval2404.imagepicker.ImagePicker

class SelectImage(
    var baseFragment: BaseActivity,
    var startCameraResult: ActivityResultLauncher<Intent>
) {


    private val TAG = "MultiImage"
    var selectedImages = ArrayList<String>()

    fun show() {
        fromGallery()
    }

    private fun fromGallery() {
        ImagePicker.with(baseFragment)
            .compress(1024)
            .crop()
            .maxResultSize(
                1080,
                1080
            )
            .createIntent { intent ->
                startCameraResult.launch(intent)
            }
    }

}