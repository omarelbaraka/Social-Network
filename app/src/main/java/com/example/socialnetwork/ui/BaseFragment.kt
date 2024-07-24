package com.example.socialnetwork.ui

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    protected var viewModel: BaseViewModel? = null

    fun onBackPressed() {
        //viewModel?.onBackPressed()
    }
}