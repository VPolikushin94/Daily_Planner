package com.example.simbirsoft.core.data.network.api

import com.example.simbirsoft.core.data.dto.NoteDto

interface NoteService {
    suspend fun getNoteList(): List<NoteDto>
}