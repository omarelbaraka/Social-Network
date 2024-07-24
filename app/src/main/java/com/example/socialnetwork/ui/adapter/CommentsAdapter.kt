package com.example.socialnetwork.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.data.model.CommentModel
import com.example.socialnetwork.databinding.CardCommentsBinding
import com.example.socialnetwork.ui.BaseViewModel


class CommentsAdapter(val context: Context,val list: ArrayList<CommentModel>, private val baseViewModel: BaseViewModel) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(binding: CardCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardCommentsBinding? = null

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardCommentsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = list[position]
        holder.binding?.userName?.text = comment.user.name
        holder.binding?.comment?.text = comment.content

    }

    override fun getItemCount(): Int {
        return list.size
    }

}