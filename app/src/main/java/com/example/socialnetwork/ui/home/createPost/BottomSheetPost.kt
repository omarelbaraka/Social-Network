package com.example.socialnetwork.ui.home.createPost

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.PostBootomSheetBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.utils.SelectImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetPost : BottomSheetDialogFragment() {
    lateinit var binding: PostBootomSheetBinding
    var viewModel: CreatePostViewModel? = null
    var necessaryPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val GALLERY_REQUEST_CODE: Int = 5000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_bootom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreatePostViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageAdd.setOnClickListener {
            /* ImagePicker.with(requireActivity())
                 .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                 .createIntentFromDialog { launcher.launch(it) }
 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                necessaryPermissions = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            if (!isPermissionsGranted(necessaryPermissions)) {
                SelectImage(
                    requireActivity() as BaseActivity,
                    startCameraResult = startCameraResult,
                ).show()
            } else {
                requestPermissionsCompat(
                    necessaryPermissions,
                    GALLERY_REQUEST_CODE
                )
            }

        }
        viewModel?.done?.observe(viewLifecycleOwner) {
            if (it) {
                this.dismiss()
            }
        }
        (viewModel as CreatePostViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }

    private val startCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                data?.data?.let {
                    val fileUri = it
                    Log.d("TAG", "startForProfileImageResult  fileUri ${fileUri.path}")

                    viewModel?.imagePath?.value = fileUri.path
                    Glide.with(requireActivity()).load(fileUri).into(binding.uploadedImage)
                }
            } else {
                Log.e("TAG", ":error task ")
            }
        }

    private fun isPermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }

    private fun requestPermissionsCompat(
        permissions: Array<String>,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
    }
}