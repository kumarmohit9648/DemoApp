package com.software96.demoapp.ui.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.software96.demoapp.databinding.TrendLoadStateFooterBinding
import com.software96.demoapp.utils.viewBindings

class TrendLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<TrendLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent.viewBindings(TrendLoadStateFooterBinding::inflate))

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(
        private val binding: TrendLoadStateFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progress.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                error.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}