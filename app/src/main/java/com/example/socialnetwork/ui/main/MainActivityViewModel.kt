package com.example.socialnetwork.ui.main

import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.onBoarding.FirstFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        fragment.postValue(FirstFragment())
    }
}