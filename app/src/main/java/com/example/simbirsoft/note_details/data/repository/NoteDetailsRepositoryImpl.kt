package com.example.simbirsoft.note_details.data.repository

import com.example.simbirsoft.core.data.db.AppDatabase
import com.example.simbirsoft.core.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_details.domain.api.NoteDetailsRepository

class NoteDetailsRepositoryImpl(
    appDatabase: AppDatabase,
    private val noteDbMapper: NoteDbMapper,
) : NoteDetailsRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun getNote(id: Int): Note {
        return noteDbMapper.map(
            noteDao.getNoteById(id)
        )
    }
}