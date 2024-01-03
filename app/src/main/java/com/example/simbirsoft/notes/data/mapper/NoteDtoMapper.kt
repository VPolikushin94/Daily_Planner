package com.example.simbirsoft.notes.data.mapper

import com.example.simbirsoft.notes.data.dto.NoteDto
import com.example.simbirsoft.notes.domain.models.Note
import java.util.Calendar

class NoteDtoMapper {

    fun map(noteDto: NoteDto): Note {
        val calendarStart = Calendar.getInstance()
        calendarStart.timeInMillis = noteDto.dateStart

        val calendarFinish = Calendar.getInstance()
        calendarStart.timeInMillis = noteDto.dateFinish
        return Note(
            noteDto.id,
            calendarStart,
            calendarFinish,
            noteDto.name,
            noteDto.description
        )
    }

    fun map(note: Note): NoteDto {
        return NoteDto(
            note.id,
            note.calendarStart.timeInMillis,
            note.calendarFinish.timeInMillis,
            note.name,
            note.description
        )
    }
}