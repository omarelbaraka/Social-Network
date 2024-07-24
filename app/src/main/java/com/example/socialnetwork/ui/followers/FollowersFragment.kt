package com.example.socialnetwork.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.FragmentFollowersBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : BaseFragment() {
    lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as FollowersViewModel

        binding.recyclerView.adapter =
            (viewModel as FollowersViewModel).getAdapter(requireContext())
        (viewModel as FollowersViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}