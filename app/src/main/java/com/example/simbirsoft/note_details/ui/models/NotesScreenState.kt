package com.example.simbirsoft.note_details.ui.models

import com.example.simbirsoft.core.domain.models.Note

sealed interface NoteDetailsScreenState {

    data object Loading : NoteDetailsScreenState

    data class Content(val note: Note) : NoteDetailsScreenState
}