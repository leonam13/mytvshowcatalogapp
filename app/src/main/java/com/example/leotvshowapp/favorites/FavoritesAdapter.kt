package com.example.leotvshowapp.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.databinding.ItemFavoriteShowBinding
import com.example.leotvshowapp.utils.loadThumb
import javax.inject.Inject

class FavoritesAdapter @Inject constructor() : RecyclerView.Adapter<FavoritesAdapter.FavoriteShowViewHolder>() {

    var shows = mutableListOf<TvShow>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((TvShow) -> Unit)? = null

    var onRemoveClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteShowViewHolder {
        return FavoriteShowViewHolder(ItemFavoriteShowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteShowViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun getItemCount(): Int = shows.size


    inner class FavoriteShowViewHolder(private val binding: ItemFavoriteShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            binding.apply {
                showContent.apply {
                    itemImage.setOnClickListener { onItemClickListener?.invoke(tvShow) }
                    itemText.text = tvShow.name
                    itemImage.loadThumb(tvShow.posterUrl, R.drawable.glide_show_fallback)
                }
                addToFavoritesTb.isChecked = true
                addToFavoritesTb.setOnCheckedChangeListener { _, isChecked ->
                    if (!isChecked) {
                        removeFromFavorite(tvShow)
                    }
                }
            }
        }

        private fun removeFromFavorite(tvShow: TvShow) {
            onRemoveClick?.invoke(tvShow.id)
            shows.remove(tvShow)
        }
    }
}