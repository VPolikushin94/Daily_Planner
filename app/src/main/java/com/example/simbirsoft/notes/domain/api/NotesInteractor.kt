package com.example.simbirsoft.notes.domain.api

import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import java.util.Calendar

interface NotesInteractor {

    suspend fun getMonthNoteList(calendar: Calendar): Resource<List<Note>>

    suspend fun getDayNoteList(calendar: Calendar): List<HourTimetableItem>
}