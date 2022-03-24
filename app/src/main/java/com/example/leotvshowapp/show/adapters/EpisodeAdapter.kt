package com.example.leotvshowapp.show.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.data.model.Episode
import com.example.leotvshowapp.databinding.SimpleItemArrowRightBinding

class EpisodeAdapter(
    private val episodes: List<Episode>,
    private val onEpisodeClick: ((Episode) -> Unit)?
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(SimpleItemArrowRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int = episodes.size

    inner class EpisodeViewHolder(private val binding: SimpleItemArrowRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.root.apply {
                setOnClickListener { onEpisodeClick?.invoke(episode) }
                text = episode.name
            }
        }
    }
}