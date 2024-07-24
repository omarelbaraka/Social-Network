package com.example.socialnetwork.ui.followers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.adapter.FollowersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor() : BaseViewModel() {
    val msg = MutableLiveData<String>("")
    private var adapter: FollowersAdapter? = null


    @Inject
    lateinit var repository: AppRepository

    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
        getFollowers()
    }

    private fun getFollowers() {
        viewModelScope.launch {
            val response = repository.getFollowers()
            adapter?.update(response.data ?: arrayListOf())
        }
    }

    fun getAdapter(context: Context): FollowersAdapter {
        if (adapter == null) {
            adapter = FollowersAdapter(context, this)
        }
        return adapter as FollowersAdapter
    }


}
