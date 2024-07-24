package com.example.socialnetwork.ui.forgetPassword

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.ForgetPasswordRequest
import com.example.socialnetwork.data.repository.AuthRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.auth.LoginFragment
import com.example.socialnetwork.ui.changePassword.ChangePasswordFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordFragmentViewModel @Inject constructor() : BaseViewModel() {

    val email = MutableLiveData<String>()
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

    fun goBack() {
        fragment.value = LoginFragment()
    }

    fun send() {
        if (email.value.isNullOrBlank()) {
            msg.value = "To proceed, please ensure that you have filled your email."
        } else {
            val forgetPasswordRequest = ForgetPasswordRequest(
                email = email.value ?: ""
            )
            viewModelScope.launch {
                val response =
                    authRepository.forgetPassword(forgottenPasswordRequest = forgetPasswordRequest)
                msg.value = response.message
                fragment.value = ChangePasswordFragment()
            }
        }

    }
}
