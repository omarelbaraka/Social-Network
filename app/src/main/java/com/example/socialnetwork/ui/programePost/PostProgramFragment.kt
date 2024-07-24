package com.example.socialnetwork.ui.programePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.FragmentPostProgramBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostProgramFragment : BaseFragment() {
    lateinit var binding: FragmentPostProgramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_program, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PostProgramFragmentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as PostProgramFragmentViewModel
        binding.recyclerView.adapter =
            (viewModel as PostProgramFragmentViewModel).getAdapter(requireContext())
        binding.recyclerViewTag.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTag.adapter =
            (viewModel as PostProgramFragmentViewModel).getAdapterTag(requireContext())
        (viewModel as PostProgramFragmentViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}