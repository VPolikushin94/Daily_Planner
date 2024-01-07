package com.example.simbirsoft.notes.ui.models

import com.applandeo.materialcalendarview.EventDay
import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.HourTimetableItem

sealed interface NotesScreenState {

    data object Loading : NotesScreenState

    data class CalendarContent(val eventList: List<EventDay>) : NotesScreenState

    data class TimetableContent(val hourTimetableList: List<HourTimetableItem>) : NotesScreenState

    data class Error(val errorType: ErrorType) : NotesScreenState

}