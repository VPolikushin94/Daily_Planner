package com.example.simbirsoft.notes.data.network.api

import com.example.simbirsoft.notes.data.dto.NoteDto

interface NoteService {
    suspend fun getNoteList(): List<NoteDto>
}