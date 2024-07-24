package com.example.socialnetwork.ui.programePost

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
import com.example.socialnetwork.ui.adapter.TagsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostProgramFragmentViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repository: AppRepository
    private var adapter: PostsAdapter? = null
    private var adapterTag: TagsAdapter? = null
    private val msg = MutableLiveData<String>("")


    fun initiateViewModel(activity: BaseActivity) {
        super.initiateView(activity)
        msg.observe(activity) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
        getProgramsPosts()
    }

    private fun getProgramsPosts() {
        viewModelScope.launch {
            val response = repository.getProgramPosts()
            adapter?.update(response.data)
        }
    }

    fun getAdapter(context: Context): PostsAdapter {
        if (adapter == null) {
            adapter = PostsAdapter(context, true, this)
        }
        return adapter as PostsAdapter
    }

    fun getAdapterTag(context: Context): TagsAdapter {
        if (adapterTag == null) {
            adapterTag = TagsAdapter(context, this)
        }
        return adapterTag as TagsAdapter
    }

    override fun onLikeClick(post: Post) {
        super.onLikeClick(post)
        viewModelScope.launch {
            val response = repository.likeOrDislikeProgramPost(postId = post.id.toString())
            msg.value = response.message ?: ""
            val data = response.data.let {
                if (it == null) {
                    null
                } else {
                    Response(user = User(id = response.data?.user?.id), postId = post.id)
                }
            }
            adapter?.updateLike(data, postId = post.id)
        }
    }

    override fun sendComment(post: Post, content: String) {
        super.sendComment(post, content)
        viewModelScope.launch {
            val response = repository.postProgramPostComment(
                idPost = post.id.toString(),
                comment = RequestCommentModel(content = content)
            )
            //msg.value = response.message ?: ""
        }
    }

    fun onTagClicked(tag: ArrayList<String>) {
        adapter?.filterByTag(tag)
    }
}