package com.example.simbirsoft.notes.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.getDatesRange
import com.example.simbirsoft.R
import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class NotesViewModel(
    private val notesInteractor: NotesInteractor,
) : ViewModel() {

    private val _screenState = MutableSharedFlow<NotesScreenState>()
    val screenState: SharedFlow<NotesScreenState> = _screenState

    fun getDayNoteList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            val dayNoteList = notesInteractor.getDayNoteList(calendar)
            _screenState.emit(NotesScreenState.TimetableContent(dayNoteList))
        }
    }

    fun getMonthNoteList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.emit(NotesScreenState.Loading)
            val monthNoteList = notesInteractor.getMonthNoteList(calendar)
            processResult(monthNoteList)
        }
    }

    private suspend fun processResult(resource: Resource<List<Note>>) {
        when (resource) {
            is Resource.Success -> {
                val events = getEventList(resource.data)
                _screenState.emit(NotesScreenState.CalendarContent(events))
            }

            is Resource.Error -> {
                resource.data?.let {
                    val events = getEventList(it)
                    _screenState.emit(NotesScreenState.CalendarContent(events))
                }
                withContext(Dispatchers.Main) {
                    _screenState.emit(NotesScreenState.Error(resource.errorType))
                }
            }
        }
    }

    private fun getEventList(noteList: List<Note>): List<EventDay> {
        val eventSet = mutableSetOf<EventDay>()
        noteList.forEach { note ->
            addEventToSet(note.calendarStart, eventSet)
            val betweenDates = note.calendarStart.getDatesRange(note.calendarFinish)
            betweenDates.forEach { calendar ->
                addEventToSet(calendar, eventSet)
            }
            addEventToSet(note.calendarFinish, eventSet)
        }
        return eventSet.toList()
    }

    private fun addEventToSet(calendar: Calendar, eventSet: MutableSet<EventDay>) {
        val eventDay = EventDay(calendar, R.drawable.ic_fire)
        eventSet.add(eventDay)
    }
}