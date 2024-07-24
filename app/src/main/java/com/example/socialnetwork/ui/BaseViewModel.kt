package com.example.socialnetwork.ui


import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialnetwork.R
import com.example.socialnetwork.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    lateinit var fragment: MutableLiveData<Fragment>
    @SuppressLint("StaticFieldLeak")
    var activity: BaseActivity? = null

    open fun initiateView(activity: BaseActivity) {
        fragment = MutableLiveData()
        this.activity = activity
        fragment.observe(
            activity
        ) { fragment: Fragment ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment, fragment.tag)
                .addToBackStack(null)
                .commit()
        }
    }

    open fun onBackPressed() {
        activity?.onBackPressed()
    }

    open fun onLikeClick(post: Post) {

    }

    open fun sendComment(post: Post, toString: String) {

    }

}