package com.example.simbirsoft.notes.data.db.mapper

import com.example.simbirsoft.notes.data.db.entity.NoteEntity
import com.example.simbirsoft.notes.domain.models.Note
import java.util.Calendar

class NoteDbMapper {

    fun map(note: Note): NoteEntity {
        return NoteEntity(
            note.id,
            note.calendarStart.timeInMillis,
            note.calendarFinish.timeInMillis,
            note.name,
            note.description
        )
    }

    fun map(noteEntity: NoteEntity): Note {
        val calendarStart = Calendar.getInstance()
        calendarStart.timeInMillis = noteEntity.dateStart

        val calendarFinish = Calendar.getInstance()
        calendarStart.timeInMillis = noteEntity.dateFinish
        return Note(
            noteEntity.id,
            calendarStart,
            calendarFinish,
            noteEntity.name,
            noteEntity.description
        )
    }
}