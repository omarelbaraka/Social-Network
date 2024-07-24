package com.example.socialnetwork.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.CardTagsBinding
import com.example.socialnetwork.ui.BaseViewModel
import com.example.socialnetwork.ui.programePost.PostProgramFragmentViewModel


class TagsAdapter(val context: Context, private val baseViewModel: BaseViewModel) :
    RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    val list: List<String> = listOf(
        "All",
        "Images filter",
        "Text addition",
        "Python",
        "Javascript",
        "Programming"
    )
    private var selectedTags = arrayListOf("All")

    class ViewHolder(binding: CardTagsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CardTagsBinding? = null

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardTagsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = list[position]
        holder.binding?.tagName?.text = tag
        if (selectedTags.contains(tag)) {
            holder.binding?.card?.setCardBackgroundColor(context.getColor(R.color.green))
        } else {
            holder.binding?.card?.setCardBackgroundColor(context.getColor(R.color.white))
        }

        holder.binding?.card?.setOnClickListener {
            if (selectedTags.contains(tag)){
                selectedTags.remove(tag)
            }else{
                selectedTags.add(tag)
                (baseViewModel as PostProgramFragmentViewModel).onTagClicked(selectedTags)
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}