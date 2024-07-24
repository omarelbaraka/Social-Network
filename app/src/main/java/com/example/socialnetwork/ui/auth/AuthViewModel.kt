package com.example.socialnetwork.ui.auth

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.LoginRequest
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.repository.AuthRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.forgetPassword.ForgetPasswordFragment
import com.example.socialnetwork.ui.home.HomeFragment
import com.example.socialnetwork.ui.register.RegisterFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : BaseViewModel() {

    val email = MutableLiveData<String>("elbarakaomar99@hotmail.com")
    val password = MutableLiveData<String>("omar123")
    val msg = MutableLiveData<String>("")


    @Inject
    lateinit var authRepository: AuthRepository

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun register() {
        fragment.value = RegisterFragment()
    }
    fun forgetPassword() {
        fragment.value = ForgetPasswordFragment()
    }

    fun login() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            msg.value =
                "To proceed, please ensure that you have filled in both your email and password. These fields are required to continue."
        } else {
            val loginRequest = LoginRequest(
                email = email.value ?: "",
                password = password.value ?: ""
            )
            viewModelScope.launch {
                val response = authRepository.login(loginRequest = loginRequest)
                Session.currentUser = response.user
                Session.accessToken = response.tokn
                fragment.value = HomeFragment()
            }
        }

    }
}
