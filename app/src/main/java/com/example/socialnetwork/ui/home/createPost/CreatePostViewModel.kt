package com.example.socialnetwork.ui.home.createPost

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repository: AppRepository
    private val msg = MutableLiveData<String>("")
    val done = MutableLiveData<Boolean>(false)
    val text = MutableLiveData<String>("")
    val imagePath = MutableLiveData<String>("")


    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createPost() {
        if (text.value.isNullOrBlank() || imagePath.value.isNullOrBlank()) {
            msg.value = "Please create some thing and select the image to continue"
            return
        }
        viewModelScope.launch {
            val response = repository.createPost(
                title = "",
                desc = text.value.toString(),
                imagesPath = listOf(imagePath.value.toString())
            )
            if (response.status=="Success"){
                done.value=true
                msg.value="Success"
            }else{
                msg.value="error"
            }
        }
    }
}