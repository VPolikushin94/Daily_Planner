package com.example.simbirsoft.notes.data.mapper

import com.example.simbirsoft.notes.data.dto.NoteDto
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.util.toMillis
import com.example.simbirsoft.util.toTimestamp
import java.util.Calendar

class NoteDtoMapper {

    fun map(noteDto: NoteDto): Note {
        val calendarStart = Calendar.getInstance()
        calendarStart.timeInMillis = noteDto.dateStart.toMillis()

        val calendarFinish = Calendar.getInstance()
        calendarFinish.timeInMillis = noteDto.dateFinish.toMillis()

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
            note.calendarStart.timeInMillis.toTimestamp(),
            note.calendarFinish.timeInMillis.toTimestamp(),
            note.name,
            note.description
        )
    }
}