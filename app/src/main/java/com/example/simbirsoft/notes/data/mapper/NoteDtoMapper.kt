package com.example.simbirsoft.notes.data.mapper

import com.example.simbirsoft.notes.data.dto.NoteDto
import com.example.simbirsoft.notes.domain.models.Note

class NoteDtoMapper {

    fun map(noteDto: NoteDto): Note {
        return Note(
            noteDto.id,
            noteDto.dateStart,
            noteDto.dateFinish,
            noteDto.name,
            noteDto.description
        )
    }

    fun map(note: Note): NoteDto {
        return NoteDto(
            note.id,
            note.dateStart,
            note.dateFinish,
            note.name,
            note.description
        )
    }
}