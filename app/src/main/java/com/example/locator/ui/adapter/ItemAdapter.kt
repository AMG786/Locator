package com.example.locator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.locator.data.room.entities.LostItem
import com.example.locator.databinding.ItemRowBinding

/**
Created by Abdul Mueez, 04/24/2025
 */
class ItemAdapter(private val onClick: (LostItem) -> Unit) :
    ListAdapter<LostItem, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    class ItemViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LostItem) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.tvDate.text = item.date
            binding.tvLocation.text = item.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class DiffCallback : DiffUtil.ItemCallback<LostItem>() {
        override fun areItemsTheSame(oldItem: LostItem, newItem: LostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LostItem, newItem: LostItem): Boolean {
            return oldItem == newItem
        }
    }
}