package com.example.socialnetwork.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.databinding.FragmentForgetPasswordBinding
import com.example.socialnetwork.databinding.FragmentProfileBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as ProfileViewModel

        (viewModel as ProfileViewModel).userProfile.observe(viewLifecycleOwner) {
            binding.email.text = it?.email
            binding.name.text = it?.name
            binding.bio.text = it?.profile?.bio
            binding.city.text = it?.profile?.city
            it?.profile?.image?.let {
                Glide.with(requireActivity()).load(it).circleCrop().into(binding.profileImage)
            }
        }


        (viewModel as ProfileViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}