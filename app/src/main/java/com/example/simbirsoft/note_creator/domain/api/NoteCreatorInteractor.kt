package com.example.simbirsoft.note_creator.domain.api

import com.example.simbirsoft.core.domain.models.Note

interface NoteCreatorInteractor {

    suspend fun saveNote(note: Note)
}