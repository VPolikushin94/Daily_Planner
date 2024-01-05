package com.example.simbirsoft.notes.domain.models

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val data: T?, val errorType: ErrorType) : Resource<T>
}
