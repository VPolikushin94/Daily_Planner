package com.example.simbirsoft.notes.data.dto

import com.google.gson.annotations.SerializedName

data class NoteDto(
    val id: Int,
    @SerializedName("date_start")
    val dateStart: Long,
    @SerializedName("date_finish")
    val dateFinish: Long,
    val name: String,
    val description: String
)
