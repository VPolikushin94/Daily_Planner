package com.example.simbirsoft.core.data.db.mapper

import com.example.simbirsoft.core.data.db.entity.NoteEntity
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.util.getFormatDateTimeWithoutYear
import com.example.simbirsoft.util.isSameDay
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

    fun map(noteEntityList: List<NoteEntity>, currentDay: Calendar): List<HourTimetableItem> {
        val timetableItemList = MutableList(24) {
            HourTimetableItem(
                it,
                mutableListOf()
            )
        }

        val calendarStart = Calendar.getInstance()
        val calendarFinish = Calendar.getInstance()

        noteEntityList.forEach { noteEntity ->
            calendarStart.timeInMillis = noteEntity.dateStart.toMillis()
            calendarFinish.timeInMillis = noteEntity.dateFinish.toMillis()

            val hourStart = if (isSameDay(calendarStart, currentDay)) {
                calendarStart.get(Calendar.HOUR_OF_DAY)
            } else {
                FIRST_HOUR
            }
            val hourFinish = if (isSameDay(calendarFinish, currentDay)) {
                calendarFinish.get(Calendar.HOUR_OF_DAY)
            } else {
                LAST_HOUR
            }

            for (hour in hourStart..hourFinish) {
                timetableItemList[hour].timetableItems.add(
                    TimetableItem(
                        noteEntity.id,
                        noteEntity.name,
                        calendarStart.time.getFormatDateTimeWithoutYear() + " - " + calendarFinish.time.getFormatDateTimeWithoutYear()
                    )
                )
            }

        }

        return timetableItemList
    }

    companion object {
        private const val FIRST_HOUR = 0
        private const val LAST_HOUR = 23
    }
}