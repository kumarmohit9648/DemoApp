package com.software96.demoapp.ui.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.software96.demoapp.data.model.GitRepo
import com.software96.demoapp.databinding.ItemTrendBinding
import com.software96.demoapp.utils.Logger
import com.software96.demoapp.utils.viewBindings

class TrendListAdapter :
    PagingDataAdapter<GitRepo, TrendListAdapter.ViewHolder>(GitRepo.COMPARATOR) {

    companion object {
        val list = mutableListOf<Long>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBindings(ItemTrendBinding::inflate))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        private val binding: ItemTrendBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GitRepo) {
            binding.apply {

                root.setOnClickListener {
                    root.isChecked = !root.isChecked
                    if (root.isChecked) list.add(item.id)
                    else list.remove(item.id)
                    Logger.log("Voodoo: $list")
                }

                root.isChecked = list.contains(item.id)

                Glide.with(itemView)
                    .load(item.owner.avatar_url)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(avatar)

                val str = SpannableString(item.owner.login + " / " + item.name)
                str.setSpan(
                    StyleSpan(Typeface.BOLD),
                    item.owner.login.length,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                name.text = str

                description.text = item.description

                language.text = item.language

                count.text = String.format("⭐ %s", item.stars)
            }
        }
    }
}