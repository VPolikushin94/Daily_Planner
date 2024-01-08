package com.example.simbirsoft.core.data.network.impl

import com.example.simbirsoft.core.data.dto.NoteDto
import com.example.simbirsoft.core.data.mapper.NoteJsonMapper
import com.example.simbirsoft.core.data.mock_data.MockNotes
import com.example.simbirsoft.core.data.network.api.NoteService

class NoteServiceImpl(
    private val mockNotes: MockNotes,
    private val noteJsonMapper: NoteJsonMapper
) : NoteService {
    override suspend fun getNoteList(): List<NoteDto> {
        return noteJsonMapper.map(mockNotes.noteJsonList)
    }
}