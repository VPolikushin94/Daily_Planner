package com.example.simbirsoft.notes.data.db.mapper

import com.example.simbirsoft.notes.data.db.entity.NoteEntity
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.util.toMillis
import com.example.simbirsoft.util.toTimestamp
import java.util.Calendar

class NoteDbMapper {

    fun map(note: Note): NoteEntity {
        return NoteEntity(
            note.id,
            note.calendarStart.timeInMillis.toTimestamp(),
            note.calendarFinish.timeInMillis.toTimestamp(),
            note.name,
            note.description
        )
    }

    fun map(noteEntity: NoteEntity): Note {
        val calendarStart = Calendar.getInstance()
        calendarStart.timeInMillis = noteEntity.dateStart.toMillis()

        val calendarFinish = Calendar.getInstance()
        calendarFinish.timeInMillis = noteEntity.dateFinish.toMillis()

        return Note(
            noteEntity.id,
            calendarStart,
            calendarFinish,
            noteEntity.name,
            noteEntity.description
        )
    }
}