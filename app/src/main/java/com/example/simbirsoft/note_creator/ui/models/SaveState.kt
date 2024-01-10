package com.example.simbirsoft.note_creator.ui.models

sealed interface SaveState {

    data object Success : SaveState

    data class Error(val saveErrorType: SaveErrorType) : SaveState
}