package com.example.simbirsoft.notes.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import java.util.Calendar

class NotesViewModel(
    private val notesInteractor: NotesInteractor,
) : ViewModel() {

    private val _screenState = MutableLiveData<NotesScreenState>()
    val screenState: LiveData<NotesScreenState> = _screenState

    fun getDayNoteList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getDayNoteList(calendar)
                .collect {
                    _screenState.postValue(
                        NotesScreenState.TimetableContent(it)
                    )
                }
        }
    }

    fun getMonthNoteList(calendar: Calendar) {
        _screenState.value = NotesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getMonthNoteList(calendar)
                .collect {
                    processResult(it)
                }
        }
    }

    private fun processResult(resource: Resource<List<Note>>) {
        when (resource) {
            is Resource.Success -> {
                val events = getEventList(resource.data)
                _screenState.postValue(
                    NotesScreenState.CalendarContent(
                        events
                    )
                )
            }

            is Resource.Error -> {
                resource.data?.let {
                    val events = getEventList(it)
                    _screenState.postValue(
                        NotesScreenState.CalendarContent(
                            events
                        )
                    )
                }
                _screenState.postValue(
                    NotesScreenState.Error(
                        resource.errorType
                    )
                )
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