package com.example.simbirsoft.notes.data.network.impl

import com.example.simbirsoft.notes.data.dto.NoteDto
import com.example.simbirsoft.notes.data.mapper.NoteJsonMapper
import com.example.simbirsoft.notes.data.mock_data.MockNotes
import com.example.simbirsoft.notes.data.network.api.NoteService

class NoteServiceImpl(
    private val mockNotes: MockNotes,
    private val noteJsonMapper: NoteJsonMapper
) : NoteService {
    override suspend fun getNoteList(): List<NoteDto> {
        return noteJsonMapper.map(mockNotes.noteJsonList)
    }
}