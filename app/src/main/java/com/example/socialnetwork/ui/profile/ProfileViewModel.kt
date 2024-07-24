package com.example.socialnetwork.ui.profile

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.ForgetPasswordRequest
import com.example.socialnetwork.data.model.ProfileDetailResponse
import com.example.socialnetwork.data.model.ResponseFollower
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.model.UserXXXXX
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.data.repository.AuthRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.auth.LoginFragment
import com.example.socialnetwork.ui.changePassword.ChangePasswordFragment
import com.example.socialnetwork.ui.editProfile.EditProfileFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel() {
    val msg = MutableLiveData<String>("")
    val userProfile = MutableLiveData<UserXXXXX?>(null)


    @Inject
    lateinit var repository: AppRepository

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
        getProfileDetail()
    }

    fun goToEditProfile() {
        fragment.value = EditProfileFragment()
    }

    private fun getProfileDetail() {
        viewModelScope.launch {
            val response = repository.getProfileDetail(Session.currentUser!!.id.toString())
            userProfile.value = response.user
        }
    }


}
