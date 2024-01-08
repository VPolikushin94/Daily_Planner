package com.example.simbirsoft.note_details.domain.api

import com.example.simbirsoft.core.domain.models.Note

interface NoteDetailsRepository {

    suspend fun getNote(id: Int): Note
}