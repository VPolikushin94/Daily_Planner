package com.example.simbirsoft.note_details.domain.impl

import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_details.domain.api.NoteDetailsInteractor
import com.example.simbirsoft.note_details.domain.api.NoteDetailsRepository

class NoteDetailsInteractorImpl(
    private val noteDetailsRepository: NoteDetailsRepository
) : NoteDetailsInteractor {

    override suspend fun getNote(id: Int): Note {
        return noteDetailsRepository.getNote(id)
    }
}