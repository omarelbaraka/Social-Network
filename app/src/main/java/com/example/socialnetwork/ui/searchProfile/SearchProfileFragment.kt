package com.example.socialnetwork.ui.searchProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.data.model.CommentModel
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.model.UserXX
import com.example.socialnetwork.databinding.FragmentFollowersBinding
import com.example.socialnetwork.databinding.FragmentSearchProfileBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProfileFragment : BaseFragment() {
    lateinit var binding: FragmentSearchProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchProfileViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as SearchProfileViewModel

        binding.recyclerView.adapter =
            (viewModel as SearchProfileViewModel).getAdapter(requireContext())
        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if ((viewModel as SearchProfileViewModel).query.value!!.isNotEmpty()) {
                    (viewModel as SearchProfileViewModel).search(binding.search.text.toString())
                }
                return@OnEditorActionListener true
            }
            false
        })
        (viewModel as SearchProfileViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }
}