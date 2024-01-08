package com.example.simbirsoft.note_details.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.note_details.domain.api.NoteDetailsInteractor
import com.example.simbirsoft.note_details.ui.models.NoteDetailsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val noteDetailsInteractor: NoteDetailsInteractor
) : ViewModel() {

    private val _screenState = MutableStateFlow<NoteDetailsScreenState>(NoteDetailsScreenState.Loading)
    val screenState: StateFlow<NoteDetailsScreenState> = _screenState

    fun getNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteDetailsInteractor.getNote(id)
            _screenState.value = NoteDetailsScreenState.Content(note)
        }
    }
}