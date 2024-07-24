package com.example.socialnetwork.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialnetwork.data.model.ResponseFollower
import com.example.socialnetwork.data.model.User
import com.example.socialnetwork.databinding.CardFollowersBinding
import com.example.socialnetwork.ui.BaseViewModel


class FollowersAdapter(val context: Context, private val baseViewModel: BaseViewModel) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    var list: ArrayList<ResponseFollower> = arrayListOf()

    class ViewHolder(binding: CardFollowersBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardFollowersBinding? = null

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardFollowersBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.binding?.userName?.text = user.name
//        holder.binding?.Bio?.text = user.bio
        user.profile?.image?.let {
            Glide.with(context).load(it).into(holder.binding!!.profileImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(data: List<ResponseFollower>) {
        this.list = arrayListOf()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

}