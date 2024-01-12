package com.example.simbirsoft.notes.data.repository

import com.example.simbirsoft.core.data.db.AppDatabase
import com.example.simbirsoft.core.data.db.entity.NoteEntity
import com.example.simbirsoft.core.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.core.data.mapper.NoteDtoMapper
import com.example.simbirsoft.core.data.network.api.NetworkClient
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.notes.data.dto.NotesRequest
import com.example.simbirsoft.notes.data.dto.NotesResponse
import com.example.simbirsoft.notes.domain.api.NotesRepository
import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.notes.domain.models.Resource
import com.example.simbirsoft.util.NetworkResultCode
import com.example.simbirsoft.util.toTimestamp
import java.util.Calendar

class NotesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val noteDtoMapper: NoteDtoMapper,
    appDatabase: AppDatabase,
    private val noteDbMapper: NoteDbMapper,
) : NotesRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun getMonthNoteList(calendar: Calendar): Resource<List<Note>> {
        val response = networkClient.request(NotesRequest())

        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        when (response.resultCode) {
            NetworkResultCode.RESULT_OK -> {
                val notesResponse = response as NotesResponse
                val noteList = notesResponse.noteList.map {
                    noteDtoMapper.map(it)
                }

                val noteEntityList = noteList.map {
                    noteDbMapper.map(it)
                }
                noteDao.insertNoteList(noteEntityList)
                getResource(noteDao.getMonthNotes(month, year))
                return getResource(noteDao.getMonthNotes(month, year))
            }

            NetworkResultCode.RESULT_ERROR -> {
                return getResource(noteDao.getMonthNotes(month, year), ErrorType.SERVER_ERROR)
            }

            else -> return getResource(noteDao.getMonthNotes(month, year), ErrorType.INTERNET_ERROR)
        }
    }

    override suspend fun getDayNoteList(calendar: Calendar): List<HourTimetableItem> {
        val date = calendar.timeInMillis.toTimestamp()
        val noteEntityList = noteDao.getDayNotes(date)
        return noteDbMapper.map(noteEntityList, calendar)
    }

    private fun getResource(
        noteEntities: List<NoteEntity>,
        errorType: ErrorType? = null,
    ): Resource<List<Note>> {
        return if (errorType == null) {
            Resource.Success(
                noteEntities.map { noteEntity ->
                    noteDbMapper.map(noteEntity)
                }
            )
        } else {
            Resource.Error(
                noteEntities.map { noteEntity ->
                    noteDbMapper.map(noteEntity)
                },
                errorType
            )
        }
    }
}
