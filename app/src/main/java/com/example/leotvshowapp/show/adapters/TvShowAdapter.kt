package com.example.leotvshowapp.show.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.databinding.SimpleItemImageTextBinding
import com.example.leotvshowapp.utils.loadThumb
import javax.inject.Inject

class TvShowAdapter @Inject constructor() :
    PagingDataAdapter<TvShow, TvShowAdapter.TvShowViewHolder>(TvShowDiffCallback()) {

    var onItemClickListener: ((TvShow) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(SimpleItemImageTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class TvShowViewHolder(private val binding: SimpleItemImageTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            binding.apply {
                itemImage.setOnClickListener { onItemClickListener?.invoke(tvShow) }
                itemText.text = tvShow.name
                itemImage.loadThumb(tvShow.posterUrl, R.drawable.glide_show_fallback)
            }
        }
    }
}

private class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}