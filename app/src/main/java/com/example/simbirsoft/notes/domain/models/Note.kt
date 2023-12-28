package com.example.simbirsoft.notes.domain.models

data class Note(
    val id: Int,
    val dateStart: Int,
    val dateFinish: Int,
    val name: String,
    val description: String
)
