package com.example.simbirsoft.notes.ui.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoft.databinding.TimetableItemBinding
import com.example.simbirsoft.notes.domain.models.TimetableItem

class TimetableItemListViewHolder(private val binding: TimetableItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: TimetableItem) {
        binding.tvNoteTime.text = model.time
        binding.tvNoteName.text = model.name
    }
}