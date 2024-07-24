package com.example.socialnetwork.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.data.model.Post
import com.example.socialnetwork.data.model.RequestCommentModel
import com.example.socialnetwork.data.model.Response
import com.example.socialnetwork.data.model.User
import com.example.socialnetwork.data.repository.AppRepository
import com.example.socialnetwork.ui.BaseActivity
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.adapter.PostsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repository: AppRepository
    private var adapter: PostsAdapter? = null
    private val msg = MutableLiveData<String>("")


    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            val response = repository.getPosts()
            adapter?.update(response.data)
        }
    }

    fun getAdapter(context: Context): PostsAdapter {
        if (adapter == null) {
            adapter = PostsAdapter(context, false, this)
        }
        return adapter as PostsAdapter
    }

    override fun onLikeClick(post: Post) {
        super.onLikeClick(post)
        viewModelScope.launch {
            val response = repository.likeOrDislike(postId = post.id.toString())
            msg.value = response.message ?: ""
            adapter?.updateLike(response.data, postId = post.id)
        }
    }

    override fun sendComment(post: Post, content: String) {
        super.sendComment(post, content)
        viewModelScope.launch {
            val response = repository.postComment(
                idPost = post.id.toString(),
                comment = RequestCommentModel(content = content)
            )
            //msg.value = response.message ?: ""
        }
    }
}