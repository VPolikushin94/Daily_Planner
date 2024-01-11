package com.example.simbirsoft.notes.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.getDatesRange
import com.example.simbirsoft.R
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.models.Resource
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private var _selectedDate: Calendar? = null

    private var isClickAllowed = true

    fun getSelectedDate(): Calendar {
        return _selectedDate ?: Calendar.getInstance()
    }

    fun getDayNoteList(calendar: Calendar) {
        _selectedDate = calendar
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.emit(NotesScreenState.Loading(false))
            val dayNoteList = notesInteractor.getDayNoteList(calendar)
            _screenState.emit(NotesScreenState.TimetableContent(dayNoteList))
        }
    }

    fun getMonthNoteList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.emit(NotesScreenState.Loading(true))
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
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, note.calendarFinish.get(Calendar.YEAR))
            calendar.set(Calendar.MONTH, note.calendarFinish.get(Calendar.MONTH))
            calendar.set(Calendar.DAY_OF_MONTH, note.calendarFinish.get(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            if (
                calendar.before(note.calendarFinish)
            ) {
                addEventToSet(note.calendarFinish, eventSet)
            }
        }
        return eventSet.toList()
    }

    private fun addEventToSet(calendar: Calendar, eventSet: MutableSet<EventDay>) {
        val eventDay = EventDay(calendar, R.drawable.ic_fire)
        eventSet.add(eventDay)
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}