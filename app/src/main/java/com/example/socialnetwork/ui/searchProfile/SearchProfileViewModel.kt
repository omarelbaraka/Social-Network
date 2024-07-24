package com.example.socialnetwork.ui.searchProfile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.ResponseFollower
import com.example.socialnetwork.data.model.User
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.adapter.FollowersAdapter
import com.example.socialnetwork.ui.adapter.UserAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProfileViewModel @Inject constructor() : BaseViewModel() {
    val msg = MutableLiveData<String>("")
    val query = MutableLiveData<String>("")
    private var adapter: UserAdapter? = null


    @Inject
    lateinit var repository: AppRepository

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getAdapter(context: Context): UserAdapter {
        if (adapter == null) {
            adapter = UserAdapter(context, this)
        }
        return adapter as UserAdapter
    }

    fun search(keyword: String) {
        viewModelScope.launch {
            val response = repository.searchProfile(keyword)
            adapter?.update(response.data.map {
                ResponseFollower(
                    id = it.id,
                    name = it.name,
                )
            } ?: arrayListOf())
        }
    }

    fun sendInvitation(user: ResponseFollower) {
        viewModelScope.launch {
            val response = repository.sendRequestToUser(idUser = user.id.toString())
            if (response.status=="Success"){
                msg.value="request Sent"
            }
        }
    }


}
