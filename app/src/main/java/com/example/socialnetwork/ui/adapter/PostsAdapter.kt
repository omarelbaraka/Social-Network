package com.example.socialnetwork.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.data.model.CommentModel
import com.example.socialnetwork.data.model.LikeObject
import com.example.socialnetwork.data.model.Post
import com.example.socialnetwork.data.model.Response
import com.example.socialnetwork.data.model.Session
import com.example.socialnetwork.data.model.UserXX
import com.example.socialnetwork.databinding.CardPostsBinding
import com.example.socialnetwork.ui.BaseViewModel
import java.util.Locale


class PostsAdapter(
    val context: Context,
    val isPostProgram: Boolean,
    private val baseViewModel: BaseViewModel
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>(), Filterable {
    var list: ArrayList<Post> = arrayListOf()
    var filterList: ArrayList<Post> = arrayListOf()

    class ViewHolder(binding: CardPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardPostsBinding? = null

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardPostsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = list[position]
        if (post.image?.isNotEmpty() == true) {
            holder.binding?.lytCode?.visibility = GONE
            holder.binding?.imagePost?.visibility = VISIBLE
            holder.binding?.imagePost?.let {
                Glide
                    .with(context)
                    .load(post.image[0])
                    .centerCrop()
                    .into(it)
            }
        } else {
            holder.binding?.imagePost?.visibility = GONE
            holder.binding?.lytCode?.visibility = VISIBLE
            holder.binding?.code?.text = post.code
        }
        holder.binding?.profileImage?.let {
            if (post.author.profileImage != null) {
                Glide
                    .with(context)
                    .load(post.author.profileImage)
                    .centerCrop()
                    .into(it)
            }
        }

        if (post.likes.isEmpty())
            Glide.with(context).load(R.drawable.thumb_up_off)
                .into(holder.binding!!.likeImage)
        post.likes?.let {
            if (it.isNotEmpty()) {
                it.forEach { like ->
                    if (like.postId == post.id) {
                        if (like.userId == Session.currentUser?.id.toString()) {
                            Glide.with(context).load(R.drawable.baseline_thumb_up)
                                .into(holder.binding!!.likeImage)
                        } else {
                            Glide.with(context).load(R.drawable.thumb_up_off)
                                .into(holder.binding!!.likeImage)
                        }
                    }
                }
            }
        }

        holder.binding?.likeImage?.setOnClickListener {
            baseViewModel.onLikeClick(post)
        }
        holder.binding?.userName?.text = post.author.name

        if (isPostProgram) {
            holder.binding?.desc?.visibility = GONE
            holder.binding?.Language?.visibility = VISIBLE
            if (post.language.equals("python")) {
                holder.binding?.let {
                    Glide.with(context).load(R.drawable.python).into(it.Language)
                }
            } else {
                holder.binding?.let {
                    Glide.with(context).load(R.drawable.js).into(it.Language)
                }
            }
        } else {
            holder.binding?.desc?.visibility = VISIBLE
            holder.binding?.Language?.visibility = GONE
            holder.binding?.desc?.text = post.title + "\n" + post.content

        }
        holder.binding?.textCount?.text =
            post.likesCount.toString() + " likes," + post.commentsCount.toString() + " Comments"
        post.comments?.let {
            if (it.isNotEmpty()) {
                val adapter = CommentsAdapter(context, it as ArrayList<CommentModel>, baseViewModel)
                holder.binding?.commentRecyeclerView?.adapter = adapter
            }
        }
        holder.binding?.search?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!holder.binding?.search?.text.toString().isNullOrEmpty()) {
                    baseViewModel.sendComment(post, holder.binding?.search?.text.toString())
                    if (list[position].comments != null) {
                        list[position].comments?.add(
                            CommentModel(
                                content = holder.binding?.search?.text.toString(), 0,
                                UserXX(Session.currentUser?.id!!, Session.currentUser?.name!!, null)
                            )
                        )
                    } else {
                        list[position].comments = ArrayList()
                        list[position].comments?.add(
                            CommentModel(
                                content = holder.binding?.search?.text.toString(), 0,
                                UserXX(Session.currentUser?.id!!, Session.currentUser?.name!!, null)
                            )
                        )
                    }
                    notifyItemChanged(position)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(data: List<Post>) {
        this.list = arrayListOf()
        this.list.addAll(data)
        this.filterList = arrayListOf()
        this.filterList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        val operationFilter: Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredList: MutableList<Post> = ArrayList<Post>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList = filterList.toMutableList()
                } else {
                    val filterPattern =
                        constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                    for (item in filterList) {
                        if (item != null && (item.content.lowercase(Locale.ROOT).contains(
                                filterPattern.lowercase(Locale.ROOT)
                            ) || item.author.name.lowercase(Locale.ROOT).contains(
                                filterPattern.lowercase(Locale.ROOT)
                            )
                                    )
                        ) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                list = results.values as ArrayList<Post>
                notifyDataSetChanged()
            }
        }
        return operationFilter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLike(data: Response?, postId: Int) {
        if (data == null) {
            list.forEach {
                if (it.id == postId) {
                    it.likesCount = it.likesCount - 1
                    it.likes.remove(
                        LikeObject(
                            postId = postId,
                            userId = Session.currentUser?.id.toString()
                        )
                    )
                    notifyDataSetChanged()
                }
            }
        } else {
            data?.let { post ->
                list.forEach {
                    if (it.id == post.postId) {
                        it.likesCount = it.likesCount + 1
                        it.likes.add(
                            LikeObject(
                                postId = post.postId,
                                userId = post.user.id.toString()
                            )
                        )
                        notifyDataSetChanged()
                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByTag(tag: ArrayList<String>) {
        if (tag.contains("All")){
            list = ArrayList()
            list.addAll(filterList)
            notifyDataSetChanged()
        }else{
            filterList.filter { it.tags?.isNotEmpty() == true && it.tags!!.let { hasElement(it,tag) } }.let {
                list = ArrayList()
                list.addAll(it)
                notifyDataSetChanged()
            }
        }

    }

    private fun hasElement(list: ArrayList<String>, tags: ArrayList<String>):Boolean{
        var hasCommonElement=false
        for (item in tags) {
            if (list.contains(item)) {
                hasCommonElement = true
                break
            }
        }
        return hasCommonElement
    }

}