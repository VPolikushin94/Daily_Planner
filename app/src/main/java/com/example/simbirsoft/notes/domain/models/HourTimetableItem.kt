package com.example.simbirsoft.notes.domain.models

data class HourTimetableItem(
    val hour: Int,
    val timetableItems: MutableList<TimetableItem>
)
