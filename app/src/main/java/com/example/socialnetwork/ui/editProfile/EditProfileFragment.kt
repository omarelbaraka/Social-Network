package com.example.socialnetwork.ui.editProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.ActivityLoginBinding
import com.example.socialnetwork.databinding.ActivityRegisterBinding
import com.example.socialnetwork.databinding.FragmentEditProfileBinding
import com.example.socialnetwork.databinding.FragmentOnbordingBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {
    lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProfileFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as EditProfileFragmentViewModel
        (viewModel as EditProfileFragmentViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}