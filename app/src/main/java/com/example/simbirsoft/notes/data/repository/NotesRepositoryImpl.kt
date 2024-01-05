package com.example.simbirsoft.notes.data.repository

import com.example.simbirsoft.notes.data.db.AppDatabase
import com.example.simbirsoft.notes.data.db.entity.NoteEntity
import com.example.simbirsoft.notes.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.notes.data.dto.NotesRequest
import com.example.simbirsoft.notes.data.dto.NotesResponse
import com.example.simbirsoft.notes.data.mapper.NoteDtoMapper
import com.example.simbirsoft.notes.data.network.api.NetworkClient
import com.example.simbirsoft.notes.domain.api.NotesRepository
import com.example.simbirsoft.notes.domain.models.ErrorType
import com.example.simbirsoft.notes.domain.models.Note
import com.example.simbirsoft.notes.domain.models.Resource
import com.example.simbirsoft.util.NetworkResultCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class NotesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val noteDtoMapper: NoteDtoMapper,
    appDatabase: AppDatabase,
    private val noteDbMapper: NoteDbMapper,
) : NotesRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun getMonthNoteList(calendar: Calendar): Flow<Resource<List<Note>>> {
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

                return noteDao.getMonthNotes(month, year).map { noteEntities ->
                    getResource(noteEntities)
                }
            }

            NetworkResultCode.RESULT_ERROR -> {
                return noteDao.getMonthNotes(month, year).map { noteEntities ->
                    getResource(noteEntities, ErrorType.SERVER_ERROR)
                }
            }

            else -> return noteDao.getMonthNotes(month, year).map { noteEntities ->
                getResource(noteEntities, ErrorType.INTERNET_ERROR)
            }
        }
    }

    override suspend fun getDayNoteList(calendar: Calendar): Flow<List<Note>> {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return noteDao.getDayNotes(day, month, year).map { noteEntityList ->
            noteEntityList.map {
                noteDbMapper.map(it)
            }
        }
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
