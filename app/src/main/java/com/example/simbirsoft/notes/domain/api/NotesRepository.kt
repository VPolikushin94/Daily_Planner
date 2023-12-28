package com.example.simbirsoft.notes.domain.api

import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource

interface NotesRepository {

    suspend fun getNoteList(): Resource<List<Note>>
}