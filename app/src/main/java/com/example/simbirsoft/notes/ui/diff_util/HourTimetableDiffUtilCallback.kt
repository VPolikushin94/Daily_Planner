package com.example.simbirsoft.notes.ui.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.example.simbirsoft.notes.domain.models.HourTimetableItem

class HourTimetableDiffUtilCallback : DiffUtil.ItemCallback<HourTimetableItem>() {
    override fun areItemsTheSame(oldItem: HourTimetableItem, newItem: HourTimetableItem): Boolean {
        return oldItem.hour == newItem.hour
    }

    override fun areContentsTheSame(
        oldItem: HourTimetableItem,
        newItem: HourTimetableItem,
    ): Boolean {
        return oldItem == newItem
    }
}