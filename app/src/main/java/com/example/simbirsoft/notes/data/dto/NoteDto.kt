package com.example.simbirsoft.notes.data.dto

import com.google.gson.annotations.SerializedName

data class NoteDto(
    val id: Int,
    @SerializedName("date_start")
    val dateStart: Int,
    @SerializedName("date_finish")
    val dateFinish: Int,
    val name: String,
    val description: String
)
