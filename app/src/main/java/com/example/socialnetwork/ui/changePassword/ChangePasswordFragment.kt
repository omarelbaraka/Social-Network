package com.example.socialnetwork.ui.changePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.FragmentChangePasswordBinding
import com.example.socialnetwork.databinding.FragmentForgetPasswordBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {
    lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ChangePasswordFragmentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as ChangePasswordFragmentViewModel
        (viewModel as ChangePasswordFragmentViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}