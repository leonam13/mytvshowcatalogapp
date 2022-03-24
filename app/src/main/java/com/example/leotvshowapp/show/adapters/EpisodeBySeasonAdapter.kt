package com.example.leotvshowapp.show.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.databinding.ItemEpisodeSeasonBinding
import javax.inject.Inject

class EpisodeBySeasonAdapter @Inject constructor() : RecyclerView.Adapter<EpisodeBySeasonAdapter.SeasonViewHolder>() {

    var onEpisodeClick: ((Episode) -> Unit)? = null

    var episodesBySeason = emptyList<Map.Entry<Int, List<Episode>>>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(ItemEpisodeSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bind(episodesBySeason[position])
    }

    override fun getItemCount(): Int = episodesBySeason.size

    inner class SeasonViewHolder(private val binding: ItemEpisodeSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Map.Entry<Int, List<Episode>>) {
            binding.apply {
                seasonNumber.apply {
                    text = root.context.getString(R.string.show_detail_episodes_season, item.key.toString())
                    setOnClickListener {
                        showEpisodesRv.visibility = if (showEpisodesRv.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        seasonNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            null,
                            null,
                            ResourcesCompat.getDrawable(
                                root.resources,
                                if (showEpisodesRv.isVisible) R.drawable.ic_arrow_up_24 else R.drawable.ic_arrow_down_24,
                                root.context.theme
                            ), null
                        )
                    }
                }
                showEpisodesRv.adapter = EpisodeAdapter(item.value, onEpisodeClick)
            }
        }
    }
}