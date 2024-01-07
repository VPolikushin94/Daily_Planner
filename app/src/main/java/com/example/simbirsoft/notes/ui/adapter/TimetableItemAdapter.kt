package com.example.simbirsoft.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.simbirsoft.databinding.TimetableItemBinding
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.notes.ui.diff_util.TimetableItemDiffUtilCallback
import com.example.simbirsoft.notes.ui.view_holder.TimetableItemListViewHolder

class TimetableItemAdapter(
    private val onItemClickListener: (TimetableItem) -> Unit
) : ListAdapter<TimetableItem, TimetableItemListViewHolder>(TimetableItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableItemListViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val view = TimetableItemBinding.inflate(layoutInspector, parent, false)
        return TimetableItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimetableItemListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(getItem(position))
        }
    }
}