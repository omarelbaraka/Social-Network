package com.example.socialnetwork.ui.onBoarding

import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.auth.LoginFragment
import com.example.socialnetwork.ui.register.RegisterFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstFragmentViewModel @Inject constructor() : BaseViewModel() {

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
    }

    fun goToLogin() {
        fragment.value = LoginFragment()
    }

    fun goToRegister() {
        fragment.value = RegisterFragment()
    }
}