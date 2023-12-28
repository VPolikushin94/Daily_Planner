package com.example.simbirsoft.notes.data.network.api

import com.example.simbirsoft.core.data.dto.Request
import com.example.simbirsoft.core.data.dto.Response


interface NetworkClient {
    suspend fun request(dto: Request): Response
}
