package com.example.simbirsoft.notes.data.repository

import com.example.simbirsoft.notes.data.db.AppDatabase
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

class NotesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val noteDtoMapper: NoteDtoMapper,
    appDatabase: AppDatabase,
    private val noteDbMapper: NoteDbMapper
) : NotesRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun getNoteList(): Resource<List<Note>> {
        val response = networkClient.request(NotesRequest())

        when (response.resultCode) {
            NetworkResultCode.RESULT_OK -> {
                val notesResponse = response as NotesResponse
                val noteList = notesResponse.noteList.map {
                    noteDtoMapper.map(it)
                }

                return Resource.Success(noteList)
            }

            NetworkResultCode.RESULT_ERROR -> {
                return Resource.Error(ErrorType.SERVER_ERROR)
            }

            else -> return Resource.Error(ErrorType.SERVER_ERROR)
        }
    }
}