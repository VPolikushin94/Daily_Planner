package com.example.simbirsoft.notes.data.dto

import com.example.simbirsoft.core.data.dto.Response

data class NotesResponse(
    val noteList: List<NoteDto>
) : Response()
