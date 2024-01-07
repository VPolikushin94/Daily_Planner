package com.example.simbirsoft.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.simbirsoft.databinding.HourTimetableItemBinding
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.notes.ui.diff_util.HourTimetableDiffUtilCallback
import com.example.simbirsoft.notes.ui.view_holder.HourTimetableListViewHolder

class HourTimetableAdapter(
    private val onItemClickListener: (TimetableItem) -> Unit
) : ListAdapter<HourTimetableItem, HourTimetableListViewHolder>(HourTimetableDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourTimetableListViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val view = HourTimetableItemBinding.inflate(layoutInspector, parent, false)
        return HourTimetableListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourTimetableListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}