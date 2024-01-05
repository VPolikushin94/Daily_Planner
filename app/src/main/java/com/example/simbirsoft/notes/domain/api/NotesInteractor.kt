package com.example.simbirsoft.notes.domain.api

import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface NotesInteractor {

    suspend fun getMonthNoteList(calendar: Calendar): Flow<Resource<List<Note>>>

    suspend fun getDayNoteList(calendar: Calendar): Flow<List<Note>>
}