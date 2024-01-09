package com.example.simbirsoft.note_creator.domain.impl

import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_creator.domain.api.NoteCreatorInteractor
import com.example.simbirsoft.note_creator.domain.api.NoteCreatorRepository

class NoteCreatorInteractorImpl(
    private val noteCreatorRepository: NoteCreatorRepository
) : NoteCreatorInteractor {
    override suspend fun saveNote(note: Note) {
        noteCreatorRepository.saveNote(note)
    }
}