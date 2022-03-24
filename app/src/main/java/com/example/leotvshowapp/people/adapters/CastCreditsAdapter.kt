package com.example.leotvshowapp.people.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.data.model.TvShow
import com.example.leotvshowapp.databinding.SimpleItemArrowRightBinding
import javax.inject.Inject

class CastCreditsAdapter @Inject constructor() : RecyclerView.Adapter<CastCreditsAdapter.CastCreditsViewHolder>() {

    var shows: List<TvShow> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShowClick: ((TvShow) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastCreditsViewHolder =
        CastCreditsViewHolder(SimpleItemArrowRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CastCreditsViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun getItemCount(): Int = shows.size

    inner class CastCreditsViewHolder(private val binding: SimpleItemArrowRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: TvShow) {
            binding.root.apply {
                setOnClickListener { onShowClick?.invoke(show) }
                text = show.name
            }
        }
    }
}