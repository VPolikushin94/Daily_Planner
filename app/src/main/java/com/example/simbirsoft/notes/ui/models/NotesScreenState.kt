package com.example.simbirsoft.notes.ui.models

import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.Note

sealed interface NotesScreenState {

    data object Loading : NotesScreenState

    data class Content(val noteList: List<Note>) : NotesScreenState

    data class Error(val errorType: ErrorType) : NotesScreenState

}