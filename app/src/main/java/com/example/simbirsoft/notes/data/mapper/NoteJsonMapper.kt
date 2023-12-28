package com.example.simbirsoft.notes.data.mapper

import com.example.simbirsoft.notes.data.dto.NoteDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoteJsonMapper(
    private val gson: Gson
) {

    fun map(json: String): List<NoteDto> {
        val type = object : TypeToken<List<NoteDto>>() {}.type
        return gson.fromJson(json, type) ?: arrayListOf()
    }

}