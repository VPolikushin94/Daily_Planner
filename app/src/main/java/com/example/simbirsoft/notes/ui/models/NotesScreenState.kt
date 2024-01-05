package com.example.simbirsoft.notes.ui.models

import com.applandeo.materialcalendarview.EventDay
import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.Note

sealed interface NotesScreenState {

    data object Loading : NotesScreenState

    data class CalendarContent(val eventList: List<EventDay>) : NotesScreenState

    data class TimetableContent(val noteList: List<Note>) : NotesScreenState

    data class Error(val errorType: ErrorType) : NotesScreenState

}