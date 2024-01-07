package com.example.simbirsoft.notes.domain.impl

import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.api.NotesRepository
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class NotesInteractorImpl(
    private val notesRepository: NotesRepository
) : NotesInteractor {

    override suspend fun getMonthNoteList(calendar: Calendar): Flow<Resource<List<Note>>> {
        return notesRepository.getMonthNoteList(calendar)
    }

    override suspend fun getDayNoteList(calendar: Calendar): Flow<List<HourTimetableItem>> {
        return notesRepository.getDayNoteList(calendar)
    }
}