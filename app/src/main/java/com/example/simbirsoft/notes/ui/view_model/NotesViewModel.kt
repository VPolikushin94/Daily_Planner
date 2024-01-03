package com.example.simbirsoft.notes.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NotesViewModel(
    private val notesInteractor: NotesInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<NotesScreenState>()
    val screenState: LiveData<NotesScreenState> = _screenState

    fun getDayNoteList(calendar: Calendar) {
        _screenState.value = NotesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val resource = notesInteractor.getNoteList()
            processResult(resource)
        }
    }

    fun getMonthNoteList() {

    }

    private fun processResult(resource: Resource<List<Note>>) {
        when (resource) {
            is Resource.Success -> {
                _screenState.postValue(
                    NotesScreenState.Content(
                        resource.data
                    )
                )
            }

            is Resource.Error -> {
                _screenState.postValue(
                    NotesScreenState.Error(
                        ErrorType.SERVER_ERROR
                    )
                )
            }
        }
    }
}