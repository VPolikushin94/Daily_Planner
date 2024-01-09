package com.example.simbirsoft.note_creator.data.repository

import com.example.simbirsoft.core.data.db.AppDatabase
import com.example.simbirsoft.core.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_creator.domain.api.NoteCreatorRepository

class NoteCreatorRepositoryImpl(
    appDatabase: AppDatabase,
    private val noteDbMapper: NoteDbMapper,
) : NoteCreatorRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun saveNote(note: Note) {
        val noteEntity = noteDbMapper.map(note)
        noteDao.insertNote(noteEntity)
    }
}