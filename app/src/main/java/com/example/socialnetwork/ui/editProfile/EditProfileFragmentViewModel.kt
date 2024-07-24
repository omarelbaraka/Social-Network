package com.example.socialnetwork.ui.editProfile

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.EditProfilSentModel
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileFragmentViewModel @Inject constructor() : BaseViewModel() {
    var name: MutableLiveData<String> = MutableLiveData("")
    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var bio: MutableLiveData<String> = MutableLiveData("")
    var city: MutableLiveData<String> = MutableLiveData("")
    private val msg = MutableLiveData<String>("")

    @Inject
    lateinit var authRepository: AppRepository

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
        name.value=Session.currentUser?.name?:""
        email.value=Session.currentUser?.email?:""
        bio.value=Session.currentUser?.bio?:""
        city.value=Session.currentUser?.city?:""
    }


    fun edit() {
        if (email.value.isNullOrBlank() ||
            name.value.isNullOrBlank()
        ) {
            msg.value =
                "To proceed, please ensure that you have filled These fields are required."
        } else {
            val registerRequest = EditProfilSentModel(
                name = name.value ?: "",
                email = email.value ?: "",
                city = city.value ?: "",
                bio = bio.value ?: "",
            )
            viewModelScope.launch {
                val response = authRepository.editProfile(registerRequest)
                if (response.status == "Success") {
                    Session.currentUser = Session.currentUser?.copy(
                        name = name.value,
                        bio = bio.value,
                        email = email.value,
                        city = city.value
                    )
                    onBackPressed()
                } else {
                    msg.value = "It appears that your account has not been created. "
                }

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}