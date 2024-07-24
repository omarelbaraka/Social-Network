package com.example.socialnetwork.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.FragmentHomeBinding
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseFragment
import com.example.socialnetwork.ui.auth.LoginFragment
import com.example.socialnetwork.ui.followers.FollowersFragment
import com.example.socialnetwork.ui.home.createPost.BottomSheetPost
import com.example.socialnetwork.ui.profile.ProfileFragment
import com.example.socialnetwork.ui.programePost.PostProgramFragment
import com.example.socialnetwork.ui.searchProfile.SearchProfileFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel as HomeFragmentViewModel
        binding.recyclerView.adapter =
            (viewModel as HomeFragmentViewModel).getAdapter(requireContext())
        /*binding.search.addTextChangedListener {
            (viewModel as HomeFragmentViewModel).getAdapter(requireContext()).filter.filter(it)
        }*/
        binding.search.setOnClickListener {
            viewModel?.fragment?.value = SearchProfileFragment()
        }
        binding.menu.setOnClickListener {
            showPopup(it)
        }
        binding.newPost.setOnClickListener {
            val bottomSheet: BottomSheetPost = BottomSheetPost()
            bottomSheet.show(
                this.parentFragmentManager, "ModalBottomSheet"
            )

        }
        (viewModel as HomeFragmentViewModel).initiateViewModel(requireActivity() as BaseActivity)
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.main_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.profile -> {
                    viewModel?.fragment?.value = ProfileFragment()
                }

                R.id.followers -> {
                    viewModel?.fragment?.value = FollowersFragment()
                }

                R.id.program -> {
                    viewModel?.fragment?.value = PostProgramFragment()
                }

                R.id.logout -> {
                    viewModel?.fragment?.value = LoginFragment()
                }
            }

            true
        })

        popup.show()
    }
}