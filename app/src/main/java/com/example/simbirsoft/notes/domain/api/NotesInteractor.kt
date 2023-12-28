package com.example.simbirsoft.notes.domain.api

import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource

interface NotesInteractor {

    suspend fun getNoteList(): Resource<List<Note>>
}