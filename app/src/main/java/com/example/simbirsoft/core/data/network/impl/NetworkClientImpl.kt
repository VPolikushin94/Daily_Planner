package com.example.simbirsoft.core.data.network.impl

import android.accounts.NetworkErrorException
import com.example.simbirsoft.core.data.dto.Request
import com.example.simbirsoft.core.data.dto.Response
import com.example.simbirsoft.notes.data.dto.NotesRequest
import com.example.simbirsoft.notes.data.dto.NotesResponse
import com.example.simbirsoft.core.data.network.api.NetworkClient
import com.example.simbirsoft.core.data.network.api.NoteService
import com.example.simbirsoft.util.NetworkResultCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkClientImpl(
    private val noteService: NoteService
) : NetworkClient {

    override suspend fun request(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = when(dto) {
                    is NotesRequest -> NotesResponse(noteService.getNoteList())
                    else -> throw NetworkErrorException("Wrong dto")
                }
                response.apply { resultCode = NetworkResultCode.RESULT_OK }
            } catch (e: Throwable) {
                Response().apply { resultCode = NetworkResultCode.RESULT_ERROR }
            }
        }
    }
}