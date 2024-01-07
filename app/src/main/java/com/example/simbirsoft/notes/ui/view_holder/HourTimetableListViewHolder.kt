package com.example.simbirsoft.notes.ui.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoft.databinding.HourTimetableItemBinding
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.notes.ui.adapter.TimetableItemAdapter

class HourTimetableListViewHolder(private val binding: HourTimetableItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: HourTimetableItem,
        onItemClickListener: (TimetableItem) -> Unit
    ) {
        binding.tvStartHour.text = model.hour.toString()
        binding.tvEndHour.text = (model.hour + 1).toString()

        val adapter = TimetableItemAdapter(onItemClickListener)
        binding.rvHourRow.adapter = adapter
        adapter.submitList(model.timetableItems)
    }
}