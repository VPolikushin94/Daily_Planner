package com.example.simbirsoft.notes.ui.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.example.simbirsoft.notes.domain.models.TimetableItem

class TimetableItemDiffUtilCallback : DiffUtil.ItemCallback<TimetableItem>() {
    override fun areItemsTheSame(oldItem: TimetableItem, newItem: TimetableItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimetableItem, newItem: TimetableItem): Boolean {
        return oldItem == newItem
    }
}