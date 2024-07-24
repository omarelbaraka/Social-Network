package com.example.socialnetwork.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.data.model.ResponseFollower
import com.example.socialnetwork.databinding.CardUsersBinding
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.searchProfile.SearchProfileViewModel


class UserAdapter(val context: Context, private val baseViewModel: BaseViewModel) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var list: ArrayList<ResponseFollower> = arrayListOf()
    var listIdRequested: ArrayList<String> = arrayListOf()

    class ViewHolder(binding: CardUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardUsersBinding? = null

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardUsersBinding.inflate(LayoutInflater.from(context), parent, false)
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

        if (listIdRequested.contains(user.id.toString())) {
            holder.binding?.sendInvitation?.backgroundTintList =
                ColorStateList.valueOf(context.getColor(R.color.gris))
            holder.binding?.sendInvitation?.text = "Pending"
            holder.binding?.sendInvitation?.isEnabled = false

        } else {
            holder.binding?.sendInvitation?.backgroundTintList =
                ColorStateList.valueOf(context.getColor(R.color.green))
            holder.binding?.sendInvitation?.text = "Send invitation"
            holder.binding?.sendInvitation?.isEnabled = true
        }

        holder.binding?.sendInvitation?.setOnClickListener {
            (baseViewModel as SearchProfileViewModel).sendInvitation(user)
            listIdRequested.add(user.id.toString())
            notifyItemChanged(position)
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