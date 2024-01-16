package com.example.simbirsoft.notes.ui.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoft.R
import com.example.simbirsoft.databinding.HourTimetableItemBinding
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.notes.ui.adapter.TimetableItemAdapter

class HourTimetableListViewHolder(private val binding: HourTimetableItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: HourTimetableItem,
        onItemClickListener: (TimetableItem) -> Unit,
    ) {
        binding.dayHourView.startHour = itemView.resources.getString(R.string.hour, model.hour)
        binding.dayHourView.finishHour = itemView.resources.getString(R.string.hour, model.hour + 1)

        val adapter = TimetableItemAdapter(onItemClickListener)
        binding.rvHourRow.adapter = adapter
        adapter.submitList(model.timetableItems)
    }
}