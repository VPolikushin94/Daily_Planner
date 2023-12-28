package com.example.simbirsoft.notes.domain.impl

import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.api.NotesRepository
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource

class NotesInteractorImpl(
    private val notesRepository: NotesRepository
) : NotesInteractor {

    override suspend fun getNoteList(): Resource<List<Note>> {
        return notesRepository.getNoteList()
    }
}