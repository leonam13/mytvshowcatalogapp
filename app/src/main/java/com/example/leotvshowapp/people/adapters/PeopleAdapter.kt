package com.example.leotvshowapp.people.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leotvshowapp.R
import com.example.leotvshowapp.data.model.Person
import com.example.leotvshowapp.databinding.SimpleItemImageTextBinding
import com.example.leotvshowapp.utils.loadThumb
import javax.inject.Inject

class PeopleAdapter @Inject constructor() : PagingDataAdapter<Person, PeopleAdapter.PeopleViewHolder>(PersonDiffCallback()) {

    var onItemClickListener: ((Person) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(SimpleItemImageTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PeopleViewHolder(private val binding: SimpleItemImageTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.apply {
                itemImage.setOnClickListener { onItemClickListener?.invoke(person) }
                itemText.text = person.name
                itemImage.loadThumb(person.imageUrl, R.drawable.glide_person_fallback)
            }
        }
    }
}

private class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}