package com.example.socialnetwork.ui.register

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.RegisterRequest
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.repository.AuthRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.auth.LoginFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterFragmentViewModel @Inject constructor() : BaseViewModel() {
    var name: MutableLiveData<String> = MutableLiveData("")
    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var bio: MutableLiveData<String> = MutableLiveData("")
    var city: MutableLiveData<String> = MutableLiveData("")
    private val msg = MutableLiveData<String>("")

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

    fun goToLogin() {
        fragment.value = LoginFragment()
    }

    fun register() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank() || bio.value.isNullOrBlank() ||
            name.value.isNullOrBlank() || city.value.isNullOrBlank()
        ) {
            msg.value =
                "To proceed, please ensure that you have filled These fields are required."
        } else {
            val registerRequest = RegisterRequest(
                name = name.value ?: "",
                email = email.value ?: "",
                city = city.value ?: "",
                bio = bio.value ?: "",
                password = password.value ?: ""
            )
            viewModelScope.launch {
                val response = authRepository.register(registerRequest)
                if (response.status == "Success") {
                    Session.currentUser = response.data
                    fragment.value = LoginFragment()
                } else {
                    msg.value = "It appears that your account has not been created. "
                }

            }
        }
    }
}