package com.example.socialnetwork.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.ActivityLoginBinding
import com.example.socialnetwork.databinding.ActivityRegisterBinding
import com.example.socialnetwork.databinding.FragmentOnbordingBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as RegisterFragmentViewModel
        (viewModel as RegisterFragmentViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}