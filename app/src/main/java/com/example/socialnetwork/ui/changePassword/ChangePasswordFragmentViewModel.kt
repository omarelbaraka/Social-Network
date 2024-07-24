package com.example.socialnetwork.ui.changePassword

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.RequestChangePassword
import com.example.socialnetwork.data.repository.AuthRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.auth.LoginFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordFragmentViewModel @Inject constructor() : BaseViewModel() {

    val accessPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
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

    fun change() {
        if (newPassword.value.isNullOrBlank() || accessPassword.value.isNullOrBlank()) {
            msg.value =
                "To proceed, please ensure that you have filled your email."
        } else {
            val request = RequestChangePassword(
                acessCode = accessPassword.value.toString(),
                password = newPassword.value.toString()
            )
            viewModelScope.launch {
                val response = authRepository.changePassword(requestChangePassword = request)
                if (response.status == "Success") {
                    msg.value =
                        "Success"
                    fragment.value = LoginFragment()
                } else {
                    msg.value =
                        "error"
                }
            }
        }

    }
}
